package com.sample.feeddata.datasource.episode

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow

interface LocalEpisodeDataSource {
    suspend fun observeEpisodes(): Flow<DataSourceResult<List<Episode>>>
    suspend fun getEpisodes(ids:List<Id>): DataSourceResult<List<Episode>>
    suspend fun saveEpisodes( episodes: List<Episode>)
    suspend fun deleteEpisodes()
}