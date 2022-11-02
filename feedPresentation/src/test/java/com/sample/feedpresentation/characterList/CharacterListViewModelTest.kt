package com.sample.feedpresentation.characterList


import androidx.lifecycle.Lifecycle
import app.cash.turbine.test
import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult
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
internal class CharacterListViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineDispatcherRule()

    private val observeCharactersListUseCase = mockk<ObserveCharactersListUseCase>()

    private val viewModel by lazy { CharactersViewModel(getCharacterListUseCase = observeCharactersListUseCase) }

    private val mockedListOfCharacters = listOf(MockDataProvider.rick, MockDataProvider.morty)


    /**
     * Scenario: Observe ViewModel UiState flow.
     *
     * Given  [DataSourceResult.InProgress] AND  [DataSourceResult.Success] with a list f characters
     *
     * When observeCharactersListUseCase(Unit) is executed
     *
     * Then  return a [ListScreenUiState]  with Loading first and Then a list of characters and isDataUpToDate == TRUE
     *
     */
    @Test
    fun getScreenUIStateWithUpToDateData() = runTest {

        coEvery { observeCharactersListUseCase(Unit) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Success(mockedListOfCharacters)
        )

        viewModel.listUiState.test {
            viewModel.lifecycleStateChanged(Lifecycle.Event.ON_RESUME)
            val inProgressOutput = awaitItem()
            val successOutput = awaitItem()

            assertEquals(true, inProgressOutput.isLoading)

            assertEquals(false, successOutput.isLoading)
            assertEquals(mockedListOfCharacters, successOutput.characters)
            assertEquals(true, successOutput.isDataUpToDate)
        }
    }

    /**
     * Scenario: Observe ViewModel UiState flow.
     *
     * Given [ObserveCharactersListUseCase] emits [DataSourceResult.Error] with a UnknownError and list of characters
     *
     * When observeCharactersListUseCase(Unit) is executed
     *
     * Then  return a [ListScreenUiState]  with list of characters and isDataUpToDate == FALSE
     *
     */

    @Test
    fun getScreenUIStateWithOutOffDateData() = runTest {

        coEvery { observeCharactersListUseCase(Unit) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Error(AppError.ApiError.UnknownError, mockedListOfCharacters)
        )

        viewModel.listUiState.test {
            viewModel.lifecycleStateChanged(Lifecycle.Event.ON_RESUME)
            val inProgressOutput = awaitItem()
            val successOutput = awaitItem()

            assertEquals(mockedListOfCharacters, successOutput.characters)
            assertEquals(false, successOutput.isDataUpToDate)
        }
    }

    /**
     * Scenario: Observe ViewModel UiState flow.
     *
     * Given [ObserveCharactersListUseCase] emits [DataSourceResult.Error] with a UnknownError and list of characters
     *
     * When observeCharactersListUseCase(Unit) is executed
     *
     * Then  a [UiMessage.ShowGenericError]  is collected
     *
     */
    @Test
    fun getUiMessageEventWithGenericError() = runTest {

        coEvery { observeCharactersListUseCase(Unit) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Error(AppError.ApiError.UnknownError, mockedListOfCharacters)
        )

        viewModel.message.test {
            viewModel.lifecycleStateChanged(Lifecycle.Event.ON_RESUME)
            val output = awaitItem()
            assertEquals(UiMessage.ShowGenericError, output)
        }
    }
}