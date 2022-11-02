package com.sample.feedframework.remote.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PaginationInfoResponseDto(
    @SerialName("count") val count: Int?,
    @SerialName("pages") val pages: Int?,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?,
)