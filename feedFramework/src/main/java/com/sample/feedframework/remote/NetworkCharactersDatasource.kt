package com.sample.feedframework.remote


import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.mapper.Mapper
import com.sample.corexdomain.mapper.toList
import com.sample.corexframework.datasource.RemoteDataSource
import com.sample.corexframework.errorParser.ErrorParser
import com.sample.feeddata.datasource.character.RemoteCharactersDataSource
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import com.sample.feedframework.remote.rest.api.ApiClient
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.PagedDataResponseDto
import com.sample.feedframework.remote.rest.mapper.PagedResponseDtoToPagedDataMapper


/**
 * Implementation of a [RemoteCharactersDataSource]
 */
internal class NetworkCharactersDatasource(
    private val apiClient: ApiClient,
    private val characterResponseDtoToCharacterModelMapper: Mapper<CharacterResponseDto, Character>,
    private val errorParser: ErrorParser,
) : RemoteCharactersDataSource {


    override suspend fun getCharactersList(): DataSourceResult<List<Character>> =
        RemoteDataSource.getRemoteResult<Unit, PagedDataResponseDto<List<CharacterResponseDto>>, List<Character>>(
            call = { apiClient.getAllCharacters() },
            mapResponseDtoToModel = { response ->
                PagedResponseDtoToPagedDataMapper(dataResponseMapper = characterResponseDtoToCharacterModelMapper.toList()).map(
                    response).data ?: listOf()
            },
            errorParser = errorParser)


    override suspend fun getCharacter(id: Id): DataSourceResult<Character> =
        RemoteDataSource.getRemoteResult<Unit, CharacterResponseDto, Character>(call = {
            apiClient.getCharacter(id.value)
        }, mapResponseDtoToModel = { response ->
            characterResponseDtoToCharacterModelMapper.map(response)
        }, errorParser = errorParser)
}