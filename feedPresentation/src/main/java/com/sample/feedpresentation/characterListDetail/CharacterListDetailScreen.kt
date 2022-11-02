package com.sample.feedpresentation.characterListDetail

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.feedpresentation.characterDetail.DetailScreen
import com.sample.feedpresentation.characterList.ListScreen

@Composable
fun CharacterListWithDetailScreen(name: String) {

        Row {
            ListScreen(
                modifier = Modifier.weight(0.5f),
                onCharacterSelected = { character ->

                }
            )
            DetailScreen(
                modifier = Modifier.weight(0.5f),
                toolbarTitle = name,
                navigateUp = { })
        }

}