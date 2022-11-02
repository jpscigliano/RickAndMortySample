package com.sample.feedframework.remote.rest.api

import com.sample.corexframework.datasource.ApiResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*

class KtorApiResponse<T>(
    private val response: HttpResponse,
    private val bodyResponse: suspend (HttpResponse) -> T,
) : ApiResponse<T> {
    override val isSuccessful: Boolean = response.status.value in 200..299
    override fun code(): Int = response.status.value
    override suspend fun body(): T? = bodyResponse(response.body())

}