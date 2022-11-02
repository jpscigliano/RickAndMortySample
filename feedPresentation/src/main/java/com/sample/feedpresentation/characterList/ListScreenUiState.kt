package com.sample.feedpresentation.characterList

import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id

data class ListScreenUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = listOf(),
    val isDataUpToDate: Boolean = false
)