package com.sample.feedframework.remote.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LocationResponseDto(
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
)
