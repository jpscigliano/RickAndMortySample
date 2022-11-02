package com.sample.feedpresentation.characterDetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult.*
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.useCase.ObserveCharacterDetailUseCase
import com.sample.feedpresentation.UiMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class CharacterDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val observeCharacterUseCase: ObserveCharacterDetailUseCase,
) : ViewModel() {

    private val characterId: Id = Id(savedStateHandle.get<String>("characterId")?.toInt() ?: 1)

    private val _DetailScreenState = MutableStateFlow(DetailScreenUiState(isLoading = true))
    val detailUiState: StateFlow<DetailScreenUiState> = _DetailScreenState

    private val _Messages = MutableSharedFlow<UiMessage>()
    val message: SharedFlow<UiMessage> = _Messages

    private var observeCharacterDetailJob: Job? = null


    fun lifecycleStateChanged(state: Lifecycle.Event) {
        if (state == Lifecycle.Event.ON_RESUME) {
            observeCharacterDetailJob?.cancel()
            observeCharacterDetailJob = observerCharacterDetail(characterId)
        }
    }

    private fun observerCharacterDetail(id: Id): Job {
        return viewModelScope.launch {
            observeCharacterUseCase(characterId).collect { useCaseResult ->
                when (useCaseResult) {
                    is InProgress -> {
                        _DetailScreenState.update { it.copy(isLoading = true) }
                    }
                    is Success, is Error -> {
                        val character = useCaseResult.dataOrNull()?.character
                        val episodes = useCaseResult.dataOrNull()?.episodes ?: listOf()
                        _DetailScreenState.update {
                            it.copy(
                                isLoading = false,
                                isDataUpToDate = useCaseResult is Success,
                                name = character?.name?.value ?: "",
                                gender = character?.gender?.name ?: "",
                                imageUrl = character?.imageUrl?.value ?: "",
                                episodes = episodes,
                                location = character?.location?.value ?: "",
                                origin = character?.origin?.value ?: ""
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