package com.sample.feedframework.remote.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PagedDataResponseDto<PaginatedData>(
    @SerialName("info") val info: PaginationInfoResponseDto?,
    @SerialName("results") val results: PaginatedData?,
)
