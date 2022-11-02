package com.sample.feedpresentation.characterDetail


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.useCase.ObserveCharacterDetailUseCase
import com.sample.feeddomain.useCase.ObserveCharactersListUseCase
import com.sample.feedpresentation.TestCoroutineDispatcherRule
import com.sample.feedpresentation.UiMessage
import com.sample.feedpresentation.mockData.MockDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
internal class CharacterDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineDispatcherRule()

    private val observeCharacterUseCase = mockk<ObserveCharacterDetailUseCase>()


    private val mockedCharacterDetail = MockDataProvider.rickWithEpisodes

    private val viewModel by lazy {
        CharacterDetailViewModel(
            savedStateHandle = SavedStateHandle(mapOf("characterId" to "${mockedCharacterDetail.character.id.value}")),
            observeCharacterUseCase = observeCharacterUseCase)
    }

    /**
     * Scenario: Observe ViewModel UiState flow.
     *
     * Given [ObserveCharacterDetailUseCase] emits [DataSourceResult.InProgress] AND  [DataSourceResult.Success] with a character wiht epidoses
     *
     * When observeCharacterUseCase(Id) is executed
     *
     * Then  collect  [DetailScreenUiState]  with a Loading  first and Then a character detail and isDataUpToDate == TRUE
     *
     */
    @Test
    fun getScreenUIStateWithUpToDateData() = runTest {

        val input = mockedCharacterDetail.character.id

        coEvery { observeCharacterUseCase(input) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Success(mockedCharacterDetail)
        )

        viewModel.detailUiState.test {
            viewModel.lifecycleStateChanged(Lifecycle.Event.ON_RESUME)
            val inProgressOutput = awaitItem()
            val successOutput = awaitItem()

            assertEquals(true, inProgressOutput.isLoading)

            assertEquals(false, successOutput.isLoading)
            assertEquals(mockedCharacterDetail.episodes, successOutput.episodes)
            assertEquals(true, successOutput.isDataUpToDate)
        }
    }

    /**
     * Scenario: Observe ViewModel UiState flow.
     *
     * Given [ObserveCharacterDetailUseCase] emits   [DataSourceResult.Error] with a UnknownError and a character with episodes
     *
     * When observeCharacterUseCase(Id) is executed
     *
     * Then  collect  [DetailScreenUiState]  with a Loading  first and Then a character detail and isDataUpToDate == FALSE
     *
     */

    @Test
    fun getScreenUIStateWithOutOffDateData() = runTest {
        val input = mockedCharacterDetail.character.id

        coEvery { observeCharacterUseCase(input) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Error(AppError.ApiError.UnknownError, mockedCharacterDetail)
        )


        viewModel.detailUiState.test {
            viewModel.lifecycleStateChanged(Lifecycle.Event.ON_RESUME)
            val inProgressOutput = awaitItem()
            val successOutput = awaitItem()

            assertEquals(true, inProgressOutput.isLoading)

            assertEquals(false, successOutput.isLoading)
            assertEquals(mockedCharacterDetail.episodes, successOutput.episodes)
            assertEquals(false, successOutput.isDataUpToDate)
        }
    }


}