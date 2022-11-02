package com.sample.feeddata.repository

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddata.datasource.episode.LocalEpisodeDataSource
import com.sample.feeddata.datasource.episode.RemoteEpisodeDataSource
import com.sample.feeddata.mockData.MockDataProvider
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class EpisodesRepositoryTest {


    private val remoteDataSource = mockk<RemoteEpisodeDataSource>()
    private val localDataSource = mockk<LocalEpisodeDataSource>()

    private val episodeRepository by lazy {
        EpisodesRepositoryImpl(
            networkEpisodesDataSource = remoteDataSource,
            localEpisodesDataSource = localDataSource
        )
    }

    private val mockedEpisodesIds: List<Id> = MockDataProvider.rick.episodesId

    private val mockedEpisodes: List<Episode> =
        listOf(MockDataProvider.episode1, MockDataProvider.episode2)

    @Before
    fun setup() {
        coEvery { localDataSource.getEpisodes(any()) } returns
                DataSourceResult.Success(mockedEpisodes)

        coEvery { remoteDataSource.getEpisodeList(any()) } returns
                DataSourceResult.Success(mockedEpisodes)

        coEvery { localDataSource.saveEpisodes(any()) } returns Unit
    }

    /**
     * Scenario: Get a list of Episodes from EpisodeRepository
     *
     * Given The a list of episodes [Id] AND that  [RemoteEpisodeDataSource] returns a Success with a list of Episodes.
     *
     * When calling getEpisodes() from EpisodeRepository
     *
     * Then a  [DataSourceResult.Success] with a list of [Episode] is returned
     *
     */

    @Test
    fun getEpisodesReturnsSuccessData() = runTest {
        val input = mockedEpisodesIds
        val result = episodeRepository.getEpisodes(input)

        // assertEquals(DataSourceResult.InProgress<List<Episode>>()::class, result.first()::class)
        assertEquals(DataSourceResult.Success(mockedEpisodes)::class, result::class)
        assertEquals(mockedEpisodes, result.dataOrNull())

    }


    /**
     * Scenario: Get list of Episodes from EpisodeRepository
     *
     * Given The [RemoteEpisodeDataSource]  return a Success with a list of episodes.
     *
     * When calling getEpisodes() from EpisodeRepository
     *
     * Then the list of episodes are save to [LocalEpisodeDataSource]
     *
     */


    @Test
    fun verifyThatEpisodeListFromRemoteIsSaveToLocal() = runTest {
        val result = episodeRepository.getEpisodes(mockedEpisodesIds)

        coVerify { localDataSource.saveEpisodes(any()) }
    }

}