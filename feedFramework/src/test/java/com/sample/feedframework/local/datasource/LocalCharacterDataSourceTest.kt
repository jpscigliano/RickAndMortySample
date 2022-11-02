package com.sample.feedframework.local.datasource

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddata.datasource.character.LocalCharactersDataSource
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id

import com.sample.feedframework.local.RoomCharactersDataSource
import com.sample.feedframework.local.room.dao.CharactersDao
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import com.sample.feedframework.local.room.mapper.CharacterEntityToModelMapper
import com.sample.feedframework.local.room.mapper.CharacterModelToCharacterWithEpisodeEntityMapper
import com.sample.feedframework.local.room.mapper.CharacterModelToEntityMapper
import com.sample.feedframework.local.room.mapper.CharacterWithEpisodesEntityToModelMapper
import com.sample.feedframework.mockData.MockDataProvider
import com.sample.feedframework.mockData.MockEntityDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
internal class LocalCharacterDataSourceTest {


    private val dao = mockk<CharactersDao>()
    private val dataSource: LocalCharactersDataSource = RoomCharactersDataSource(
        charactersDao = dao,
        characterEntityToModelMapper = CharacterEntityToModelMapper(),
        characterWithEpisodesEntityToModelMapper = CharacterWithEpisodesEntityToModelMapper(),
        characterModelToEntityMapper = CharacterModelToEntityMapper(),
        characterModelToCharacterWithEpisodeEntityMapper = CharacterModelToCharacterWithEpisodeEntityMapper())


    private val mockedCharacter = MockDataProvider.rick

    @Before
    fun setup() {
        coEvery {
            dao.getCharactersWithEpisodes(mockedCharacter.id.value)
        } returns flowOf(MockEntityDataProvider.characterWithEpisodeEntity)
    }

    /**
     * Scenario: Get a character from the Local DB.
     *
     * Given That a call to the dao emits [CharacterWithEpisodesEntity]
     *
     * When the method getCharacter([Id])  from LocalData Source is executed
     *
     * Then [DataSourceResult.Success] with a [Character]  is emitted
     *
     */
    @Test
    fun getASuccessResultWithCharacter() = runTest {
        val input = mockedCharacter.id

        val result = dataSource.getCharacter(input).toList()

        assertEquals(DataSourceResult.Success(mockedCharacter)::class, result.first()::class)
        assertEquals(mockedCharacter, result.first().dataOrNull())
    }


}