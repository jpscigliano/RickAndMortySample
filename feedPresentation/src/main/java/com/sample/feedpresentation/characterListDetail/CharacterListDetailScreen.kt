package com.sample.feedpresentation.characterListDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.feeddomain.model.Character
import com.sample.feedpresentation.characterDetail.CharacterDetailView
import com.sample.feedpresentation.characterDetail.DetailScreenUiState
import com.sample.feedpresentation.characterList.CharactersListView
import com.sample.feedpresentation.characterList.characters
import com.sample.feedpresentation.theme.RickAndMortyTheme


//TODO Unfortunately I did not found enough time to work on the UI for Tablets / Landscape. Coming soon.

@Composable
fun CharacterListWithDetailScreen(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    characterSelected: DetailScreenUiState,
) {

    Row(modifier = modifier) {
        Box(modifier = Modifier
            .weight(0.3f)
        ) {
            CharactersListView(
                modifier = Modifier,
                characters = characters
            )
        }
        Divider(modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(), color = Color.Gray)
        Box(modifier = Modifier.weight(0.7f)) {
            CharacterDetailView(Modifier,
                uiState = characterSelected,
                showContent = true)
        }

    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 600)
private fun MyCharacterListWithDetailScreen() {
    RickAndMortyTheme {
        CharacterListWithDetailScreen(
            characters = characters(),
            characterSelected = DetailScreenUiState(
                isDataUpToDate = true,
                isLoading = false,
                name = "Rick Sanchez",
                gender = "Male",
                imageUrl = "",
                state = "Alive ",
                location = "Earth",
                origin = "Earth",
                episodes = listOf())
        )
    }
}