package com.sample.feedframework.remote.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CharacterResponseDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("status") val status: String?,
    @SerialName("image") val image: String?,
    @SerialName("species") val species: String?,
    @SerialName("type") val type: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("location") val location: LocationResponseDto,
    @SerialName("origin") val origin: LocationResponseDto,
    @SerialName("episode") val episodes: List<String>?,
)
