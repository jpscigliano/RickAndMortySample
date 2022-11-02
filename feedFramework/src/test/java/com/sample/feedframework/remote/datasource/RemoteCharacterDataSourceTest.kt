package com.sample.feedframework.remote.datasource

import com.sample.corexframework.datasource.ApiResponse
import com.sample.feedframework.mockData.MockResponseDtoDataProvider
import com.sample.feedframework.remote.rest.api.ApiClient
import com.sample.feedframework.remote.rest.api.KtorApiResponse
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto
import com.sample.feedframework.remote.rest.dto.PagedDataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test


@ExperimentalCoroutinesApi
internal class RemoteCharacterDataSourceTest {

    /**
     * Scenario: Get a character by Id from HttpClient
     *
     * Given a mock [ApiClient] And  JsonResponse
     *
     * When getCharacter(id) is executed
     *
     * Then  return a [CharacterResponseDto]
     *
     */
    @Test
    fun getCharacterFromMockHttpClient()  = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(MockResponseDtoDataProvider.characterJson),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val apiClient: ApiClient = MockApiClient(mockEngine)

        assertEquals(MockResponseDtoDataProvider.rickResponse, apiClient.getCharacter(1).body())
    }

    /**
     * Scenario: Get a list of character  from HttpClient
     *
     * Given a mock [ApiClient] And  JsonResponse
     *
     * When getAllCharacters() is executed
     *
     * Then  return a a list of [CharacterResponseDto]
     *
     */
    @Test
    fun getCharacterListFromMockHttpClient()  = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(MockResponseDtoDataProvider.characterJsonArray),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val apiClient: ApiClient = MockApiClient(mockEngine)

        assertEquals(MockResponseDtoDataProvider.rickResponse, apiClient.getAllCharacters().body()?.results?.first() )
    }

}



class MockApiClient(engine: HttpClientEngine) : ApiClient {
    private val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getAllCharacters(): ApiResponse<PagedDataResponseDto<List<CharacterResponseDto>>> {
        return KtorApiResponse(httpClient.get("/")) { it.body() }
    }

    override suspend fun getCharacter(id: Int): ApiResponse<CharacterResponseDto> {
        return KtorApiResponse(httpClient.get("/")) { it.body() }
    }

    override suspend fun getEpisodes(ids: List<Int>): ApiResponse<List<EpisodeResponseDto>> {
        return KtorApiResponse(httpClient.get("/")) { it.body() }
    }

}