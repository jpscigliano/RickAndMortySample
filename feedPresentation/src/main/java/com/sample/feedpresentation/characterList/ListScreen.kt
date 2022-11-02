package com.sample.feedpresentation.characterList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.feeddomain.model.*
import com.sample.feedpresentation.R
import com.sample.feedpresentation.UiMessage
import com.sample.feedpresentation.common.AsyncImage
import com.sample.feedpresentation.common.DefaultSnackbar
import com.sample.feedpresentation.common.Loading
import com.sample.feedpresentation.common.Toolbar
import com.sample.feedpresentation.extensions.OnLifecycleEvent
import com.sample.feedpresentation.extensions.observeWithLifecycle
import com.sample.feedpresentation.extensions.rememberStateWithLifecycle
import com.sample.feedpresentation.theme.RickAndMortyTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onCharacterSelected: (character: Character) -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val viewModel: CharactersViewModel = getViewModel()
    val viewState: ListScreenUiState by rememberStateWithLifecycle(viewModel.listUiState)
    val context = LocalContext.current


    OnLifecycleEvent { _, event ->
        viewModel.lifecycleStateChanged(event)
    }

    viewModel.message.observeWithLifecycle {
        val message = when (it) {
            is UiMessage.ShowCharacterInvalidMessage -> context.getString(R.string.error_invalid_character)
            is UiMessage.ShowGenericError -> context.getString(R.string.error_generic)
            is UiMessage.ShowNoInternetMessage -> context.getString(R.string.error_no_internet)
            is UiMessage.UnableToFetchData -> context.getString(R.string.error_unable_to_fetch)
        }
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short)
    }

    ListScreen(
        modifier = modifier,
        scaffoldState = scaffoldState,
        listScreenUiModel = viewState,
        onCharacterSelected = onCharacterSelected)

}

@Composable
internal fun ListScreen(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    listScreenUiModel: ListScreenUiState,
    onCharacterSelected: (character: Character) -> Unit,
) {

    Scaffold(topBar = {
        Toolbar(
            title = "Rick Friends",
            showBackArrow = false,
        )
    }, modifier = modifier, content = {
        ListScreenContent(modifier = Modifier
            .background(
                if (listScreenUiModel.isDataUpToDate) MaterialTheme.colors.background else MaterialTheme.colors.error
            )
            .padding(it),
            showContent = listScreenUiModel.characters.isEmpty().not(),
            characters = listScreenUiModel.characters,
            onCharacterSelected = onCharacterSelected)

        Loading(showLoading = listScreenUiModel.isLoading)


    }, scaffoldState = scaffoldState, snackbarHost = { DefaultSnackbar(snackbarHostState = it) })
}

@Composable
private fun ListScreenContent(
    modifier: Modifier = Modifier,
    showContent: Boolean,
    characters: List<Character>,
    onCharacterSelected: (character: Character) -> Unit = {},
) {
    AnimatedVisibility(visible = showContent,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier.padding(horizontal = 15.dp),
            contentPadding = PaddingValues(vertical = 30.dp),
        ) {
            items(items = characters, itemContent = { character ->
                CharacterView(
                    character = character,
                    onCharacterSelected = onCharacterSelected)
            })
        }
    }
}

@Composable
private fun CharacterView(
    character: Character,
    onCharacterSelected: (character: Character) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onCharacterSelected(character) }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val imageUrl = character.imageUrl?.value ?: ""
            val name: String = character.name?.value ?: ""

            AsyncImage(model = imageUrl,
                requestBuilder = { crossfade(true) },
                contentDescription = name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(5)))

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = name, style = MaterialTheme.typography.h2)
            }
        }
    }

}


@Composable
@Preview
private fun MyCharacterView() {
    RickAndMortyTheme {
        CharacterView(character = Character(id = Id(1),
            name = Name("Rick"),
            imageUrl = null,
            status = Status.ALIVE,
            gender = Gender.MALE,
            specie = Specie.HUMANOID,
            origin = Name("Earth"),
            location = Name("Earth"),
            episodesId = listOf(Id(1), Id(2))
        )) {}
    }
}

@Composable
@Preview
private fun MyListScreen() {
    RickAndMortyTheme {
        ListScreenContent(showContent = true,
            characters = listOf(Character(id = Id(1),
                name = Name("Rick"),
                imageUrl = null,
                status = Status.ALIVE,
                gender = Gender.MALE,
                specie = Specie.HUMANOID,
                origin = Name("Earth"),
                location = Name("Earth"),
                episodesId = listOf(Id(1), Id(2))
            ),
                Character(id = Id(2),
                    name = Name("Morty"),
                    imageUrl = null,
                    status = Status.ALIVE,
                    gender = Gender.MALE,
                    specie = Specie.HUMANOID,
                    origin = Name("Earth"),
                    location = Name("Earth"),
                    episodesId = listOf(Id(1), Id(2)))))
    }
}
