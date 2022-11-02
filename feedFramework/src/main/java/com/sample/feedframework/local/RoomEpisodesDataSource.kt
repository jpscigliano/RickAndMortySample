package com.sample.feedframework.local

import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.mapper.Mapper
import com.sample.corexdomain.mapper.toList
import com.sample.corexframework.datasource.LocalDataSource
import com.sample.feeddata.datasource.episode.LocalEpisodeDataSource
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feedframework.local.room.dao.EpisodesDao
import com.sample.feedframework.local.room.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of a [LocalEpisodeDataSource] that uses Room to store and retrieve [EpisodeEntity]
 */
internal class RoomEpisodesDataSource(
    private val episodesDao: EpisodesDao,
    private val episodeEntityToModelMapper: Mapper<EpisodeEntity, Episode>,
    private val episodeModelToEntityMapper: Mapper<Episode, EpisodeEntity>,
) : LocalEpisodeDataSource {

    override suspend fun observeEpisodes(): Flow<DataSourceResult<List<Episode>>> =
        LocalDataSource.getLocalResultAsFlow(
            call = { episodesDao.getAll() },
            entityToModelMapper = { result -> episodeEntityToModelMapper.toList().map(result) }
        )


    override suspend fun getEpisodes(ids: List<Id>): DataSourceResult<List<Episode>> =
        LocalDataSource.getLocalResult(
            call = { episodesDao.get(ids.map { it.value }) },
            entityToModelMapper = { result -> episodeEntityToModelMapper.toList().map(result) }
        )


    override suspend fun saveEpisodes(episodes: List<Episode>) =
        LocalDataSource.insertToLocal(
            transformModelToEntity = { episodeModelToEntityMapper.toList().map(episodes) },
            call = { entity -> episodesDao.insert(entity) }
        )

    override suspend fun deleteEpisodes() {
        episodesDao.deleteAll()
    }
}