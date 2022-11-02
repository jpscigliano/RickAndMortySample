package com.sample.feeddata.datasource.episode

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id

interface RemoteEpisodeDataSource {
    suspend fun getEpisodeList(ids:List<Id>): DataSourceResult<List<Episode>>
}