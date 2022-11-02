package com.sample.feedframework.remote.rest.api

import com.sample.corexframework.datasource.ApiResponse
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto
import com.sample.feedframework.remote.rest.dto.PagedDataResponseDto

interface ApiClient {
    suspend fun getAllCharacters(): ApiResponse<PagedDataResponseDto<List<CharacterResponseDto>>>
    suspend fun getCharacter(id: Int): ApiResponse<CharacterResponseDto>
    suspend fun getEpisodes(ids: List<Int>): ApiResponse<List<EpisodeResponseDto>>
}