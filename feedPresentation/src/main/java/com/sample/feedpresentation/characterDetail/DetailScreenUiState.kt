package com.sample.feedpresentation.characterDetail

import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Character

data class DetailScreenUiState(
    val isLoading: Boolean = false,
    val isDataUpToDate: Boolean = false,
    val name: String = "",
    val gender: String = "",
    val imageUrl: String = "",
    val state: String = "",
    val location: String = "",
    val origin: String = "",
    val episodes: List<Episode> = listOf(),
)

enum class Characteristics {
    NAME, GENDER, STATE, LOCATION, ORIGIN
}