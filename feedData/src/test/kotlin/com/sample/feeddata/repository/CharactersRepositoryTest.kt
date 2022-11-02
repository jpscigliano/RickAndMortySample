package com.sample.feeddata.repository

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddata.datasource.character.LocalCharactersDataSource
import com.sample.feeddata.datasource.character.RemoteCharactersDataSource
import com.sample.feeddata.mockData.MockDataProvider
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class CharactersRepositoryTest {


    private val remoteCharactersDataSource = mockk<RemoteCharactersDataSource>()
    private val localCharactersDataSource = mockk<LocalCharactersDataSource>()

    private val charactersRepository by lazy {
        CharactersRepositoryImpl(
            networkCharactersDataSource = remoteCharactersDataSource,
            localCharactersDataSource = localCharactersDataSource
        )
    }

    private val mockedCharacters = listOf(MockDataProvider.rick, MockDataProvider.morty)
    private val mockedCharacter: Character = MockDataProvider.rick

    @Before
    fun setup() {
        coEvery { remoteCharactersDataSource.getCharactersList() } returns
                DataSourceResult.Success(mockedCharacters)

        coEvery { localCharactersDataSource.observerCharactersList() } returns flowOf(
            DataSourceResult.Success(mockedCharacters)
        )
        coEvery { localCharactersDataSource.saveCharacters(any()) } returns Unit


        coEvery { remoteCharactersDataSource.getCharacter(mockedCharacter.id) } returns
                DataSourceResult.Success(mockedCharacter)

        coEvery { localCharactersDataSource.getCharacter(mockedCharacter.id) } returns flowOf(
            DataSourceResult.Success(mockedCharacter)
        )

        coEvery { localCharactersDataSource.saveCharacter(any()) } returns Unit

    }

    /**
     * Scenario: Collect list of Characters from CharactersRepository
     *
     * Given The [RemoteCharactersDataSource] emits a Success with a list of characters.
     *
     * When  calling observeCharacters() from CharacterRepository
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Success] with a list of [Character] is collected.
     *
     */

    @Test
    fun getCharactersReturnsInProgressAndSuccessData() = runTest {

        val result = charactersRepository.observeCharacters().toList()

        assertEquals(DataSourceResult.InProgress<List<Character>>()::class, result.first()::class)
        assertEquals(DataSourceResult.Success(mockedCharacters)::class, result[1]::class)
        assertEquals(mockedCharacters, result[1].dataOrNull())

    }


    /**
     * Scenario: Collect list of Characters from CharactersRepository
     *
     * Given The [RemoteCharactersDataSource] emits a Success with a list of characters.
     *
     * When  observeCharacters() from CharacterRepository is call
     *
     * Then  characters are save to [LocalCharactersDataSource]
     *
     */

    @Test
    fun verifyThatCharacterListFromRemoteIsSaveToLocal() = runTest {
        val result = charactersRepository.observeCharacters().toList()

        coVerify { localCharactersDataSource.saveCharacters(any()) }
    }


    /**
     * Scenario: Collect a Character from CharactersRepository
     *
     * Given A [Id] of a Character And  [RemoteCharactersDataSource] emits a Success with character.
     *
     * When calling observeCharacter(Id) from CharacterRepository
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Success] with a  [Character] is collected.
     *
     */

    @Test
    fun getCharacterDetailReturnsInProgressAndSuccessData() = runTest {
        val input = mockedCharacter.id
        val result = charactersRepository.observeCharacter(input).toList()

        assertEquals(DataSourceResult.InProgress<Character>()::class, result.first()::class)
        assertEquals(DataSourceResult.Success(mockedCharacter)::class, result[1]::class)
        assertEquals(mockedCharacter, result[1].dataOrNull())

    }


    /**
     * Scenario: Collect a Character from CharactersRepository
     *
     * Given A [Id] of a Character And  [RemoteCharactersDataSource] emits a Success with character.
     *
     * When  calling observeCharacters() from CharacterRepository
     *
     * Then  the character is save to [LocalCharactersDataSource]
     *
     */

    @Test
    fun verifyThatCharacterDetailFromRemoteIsSaveToLocal() = runTest {
        val input = mockedCharacter.id
        val result = charactersRepository.observeCharacter(input).toList()

        coVerify { localCharactersDataSource.saveCharacter(any()) }
    }
}