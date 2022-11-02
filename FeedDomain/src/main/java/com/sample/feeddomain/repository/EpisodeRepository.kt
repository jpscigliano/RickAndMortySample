package com.sample.feeddomain.repository

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    suspend fun getEpisodes(episodeId: List<Id>): DataSourceResult<List<Episode>>
}
