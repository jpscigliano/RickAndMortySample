package com.sample.feedframework.local.datasource

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sample.feedframework.local.room.dao.CharactersDao
import com.sample.feedframework.local.room.dao.EpisodesDao
import com.sample.feedframework.local.room.database.RickAndMortyDatabase
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import com.sample.feedframework.local.room.entity.EpisodeEntity
import com.sample.feedframework.mockData.MockEntityDataProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
internal class RoomTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var characterDao: CharactersDao
    private lateinit var episodeDao: EpisodesDao
    private lateinit var database: RickAndMortyDatabase


    private val listOfMockedEpisodesEntity =
        listOf(MockEntityDataProvider.episode1Entity, MockEntityDataProvider.episode2Entity)


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RickAndMortyDatabase::class.java)
            .allowMainThreadQueries().build()
        characterDao = database.characterDao()
        episodeDao = database.episodeDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    /**
     * Scenario: Insert a Character Entity and read it back.
     *
     * Given an Instance of the  Room database And a character entity Insert it on it.
     *
     * When  getAll()  is called
     *
     * Then  the  same[CharacterEntity]  is emitted
     *
     */
    @Test
    fun writeCharacterEntityAndReadIt() = runTest {
        val input = MockEntityDataProvider.mortyEntity

        characterDao.insert(input)

        val result = characterDao.getAll().first()

        assertEquals(MockEntityDataProvider.mortyEntity, result.first())
    }

    /**
     * Scenario: Insert a [CharacterEntity] and a list of [EpisodeEntity] and read it back
     *
     * Given an Instance of the Room database with Character and Episodes
     *
     * When  getCharactersWithEpisodes(id) is called
     *
     * Then a  [CharacterWithEpisodesEntity]  is emitted.
     *
     */
    @Test
    fun writeACharacterWithEpisodesAndReadIt() = runTest {
        val input = MockEntityDataProvider.rickEntity
        val input2 = listOfMockedEpisodesEntity
        val input3 = MockEntityDataProvider.characterEpisodeCrossRef

        val expected = MockEntityDataProvider.characterWithEpisodeEntity

        characterDao.insert(input)
        episodeDao.insert(input2)
        characterDao.insertCharacterWithEpisodes(input3)

        val result = characterDao.getCharactersWithEpisodes(input.characterId).first()

        assertEquals(expected, result)
    }
}