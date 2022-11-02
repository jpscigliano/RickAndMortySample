package com.sample.feedframework.remote.rest.api

import com.sample.corexframework.datasource.ApiResponse
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto
import com.sample.feedframework.remote.rest.dto.PagedDataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


private const val BASE_URL = "https://rickandmortyapi.com/api"
const val GET_CHARACTERS = "$BASE_URL/character"
const val GET_EPISODES = "$BASE_URL/episode"

class ApiClientImpl(private val httpClient: HttpClient) : ApiClient {
    override suspend fun getAllCharacters(): ApiResponse<PagedDataResponseDto<List<CharacterResponseDto>>> {
        return KtorApiResponse(httpClient.get { url(GET_CHARACTERS) }) { it.body() }
    }

    override suspend fun getCharacter(id: Int): ApiResponse<CharacterResponseDto> {
        return KtorApiResponse(httpClient.get { url("${GET_CHARACTERS}/${id}") }) { it.body() }
    }

    override suspend fun getEpisodes(ids: List<Int>): ApiResponse<List<EpisodeResponseDto>> {
        return KtorApiResponse(
            httpClient.get {
                url("${GET_EPISODES}/${ids.joinToString(",") { "$it" }},")
            }
        ) { it.body() }
    }
}