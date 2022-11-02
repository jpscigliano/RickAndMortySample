package com.sample.feedpresentation.characterList

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult.*
import com.sample.feeddomain.useCase.ObserveCharactersListUseCase
import com.sample.feedpresentation.UiMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class CharactersViewModel(
    private val getCharacterListUseCase: ObserveCharactersListUseCase,
) : ViewModel() {

    private val _ListScreenState: MutableStateFlow<ListScreenUiState> =
        MutableStateFlow(ListScreenUiState(isLoading = true))
    val listUiState: StateFlow<ListScreenUiState> = _ListScreenState

    private val _Messages = MutableSharedFlow<UiMessage>()
    val message: SharedFlow<UiMessage> = _Messages

    private var observeCharactersJob: Job? = null


    fun lifecycleStateChanged(state: Lifecycle.Event) {
        if (state == Lifecycle.Event.ON_RESUME) {
            observeCharactersJob?.cancel()
            observeCharactersJob = observerCharacters()
        }
    }

    private fun observerCharacters(): Job {
        return viewModelScope.launch {

            getCharacterListUseCase(Unit).collect { useCaseResult ->
                when (useCaseResult) {
                    is InProgress -> {
                        _ListScreenState.update { it.copy(isLoading = true) }
                        delay(500) //some seconds to appreciate the loading animation :)
                    }
                    is Success, is Error -> {
                        _ListScreenState.update {
                            it.copy(
                                isLoading = false,
                                characters = useCaseResult.dataOrNull() ?: listOf(),
                                isDataUpToDate = useCaseResult is Success
                            )
                        }
                        if (useCaseResult is Error) {
                            _Messages.emit(when (useCaseResult.error) {
                                is AppError.CharacterUnavailable -> UiMessage.ShowCharacterInvalidMessage
                                is AppError.NoInternetAvailable -> UiMessage.ShowNoInternetMessage
                                is AppError.ApiError -> UiMessage.ShowGenericError
                            })
                        }
                    }
                }
            }
        }
    }


}