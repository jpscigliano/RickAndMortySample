package com.sample.feedframework.remote


import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.mapper.Mapper
import com.sample.corexdomain.mapper.toList
import com.sample.corexframework.datasource.RemoteDataSource
import com.sample.corexframework.errorParser.ErrorParser
import com.sample.feeddata.datasource.character.RemoteCharactersDataSource
import com.sample.feeddata.datasource.episode.RemoteEpisodeDataSource
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feedframework.remote.rest.api.ApiClient
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto

/**
 * Implementation of a [RemoteCharactersDataSource]
 */
internal class NetworkEpisodesDatasource(
    private val apiClient: ApiClient,
    private val episodeResponseDtoToEpisodeModelMapper: Mapper<EpisodeResponseDto, Episode>,
    private val errorParser: ErrorParser,
) : RemoteEpisodeDataSource {

    override suspend fun getEpisodeList(ids: List<Id>): DataSourceResult<List<Episode>> {
        return RemoteDataSource.getRemoteResult<Unit, List<EpisodeResponseDto>, List<Episode>>(
            call = { apiClient.getEpisodes(ids.map { it.value }) },
            mapResponseDtoToModel = { response ->
                episodeResponseDtoToEpisodeModelMapper.toList().map(response)
            },
            errorParser = errorParser)
    }

}