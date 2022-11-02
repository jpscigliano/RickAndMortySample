package com.sample.feeddomain.useCase

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.mockData.MockDataProvider
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.repository.CharactersRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ObserveCharactersListUseCaseTest {


    private val charactersRepository = mockk<CharactersRepository>()

    private val observeCharactersListUseCase by lazy { ObserveCharactersListUseCase(feedRepository = charactersRepository) }

    private val mockedCharacters = listOf(MockDataProvider.rick, MockDataProvider.morty)


    @Before
    fun setup() {
        coEvery { charactersRepository.observeCharacters() } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Success(mockedCharacters),
        )
    }

    /**
     * Scenario: User observes list of characters.
     *
     * Given The repository emits InProgress And Success with a list of characters.
     *
     * When [ObserveCharactersListUseCase] is executed
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Success] with a list of [Character] is collected.
     *
     */

    @Test
    fun getSuccessResultWithListOfCharactersData() = runTest {
        val input = Unit
        val result = observeCharactersListUseCase(input).toList()

        assertEquals(DataSourceResult.InProgress<List<Character>>()::class, result.first()::class)
        assertEquals(DataSourceResult.Success(mockedCharacters)::class, result[1]::class)
        assertEquals(mockedCharacters, result[1].dataOrNull())
    }

}