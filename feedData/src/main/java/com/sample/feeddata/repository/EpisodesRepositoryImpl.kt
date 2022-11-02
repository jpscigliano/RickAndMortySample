package com.sample.feeddata.repository

import com.sample.corexdata.fetchAndReturnFromLocalDatasource
import com.sample.corexdomain.DataSourceResult
import com.sample.feeddata.datasource.episode.LocalEpisodeDataSource
import com.sample.feeddata.datasource.episode.RemoteEpisodeDataSource
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.repository.EpisodeRepository

/**
 * Implementation of a [EpisodeRepository] that it's in charge of retrieving episodes from local or remote.
 */
internal class EpisodesRepositoryImpl(
    private val networkEpisodesDataSource: RemoteEpisodeDataSource,
    private val localEpisodesDataSource: LocalEpisodeDataSource,
) : EpisodeRepository {

/*
    override suspend fun observeEpisodes(episodeId: List<Id>): Flow<DataSourceResult<List<Episode>>> {
        return fetchAndObserveLocalDatasource(
            selectQuery = { localEpisodesDataSource.observeEpisodes() },
            networkCall = { networkEpisodesDataSource.getEpisodeList(episodeId) },
            insertResultQuery = { localEpisodesDataSource.saveEpisodes(it) }
        )
    }

 */

    override suspend fun getEpisodes(episodeId: List<Id>): DataSourceResult<List<Episode>> {
        return fetchAndReturnFromLocalDatasource(
            selectQuery = { localEpisodesDataSource.getEpisodes(episodeId) },
            networkCall = { networkEpisodesDataSource.getEpisodeList(episodeId) },
            insertResultQuery = { localEpisodesDataSource.saveEpisodes(it) }
        )
    }
}