package com.sample.feedpresentation.characterDetail


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.model.Name
import com.sample.feedpresentation.R
import com.sample.feedpresentation.UiMessage
import com.sample.feedpresentation.common.AsyncImage
import com.sample.feedpresentation.common.DefaultSnackbar
import com.sample.feedpresentation.common.Loading
import com.sample.feedpresentation.common.Toolbar
import com.sample.feedpresentation.extensions.OnLifecycleEvent
import com.sample.feedpresentation.extensions.ifNotEmpty
import com.sample.feedpresentation.extensions.observeWithLifecycle
import com.sample.feedpresentation.extensions.rememberStateWithLifecycle
import com.sample.feedpresentation.theme.RickAndMortyTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    toolbarTitle: String,
    navigateUp: () -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val viewModel: CharacterDetailViewModel = getViewModel()
    val viewState: DetailScreenUiState by rememberStateWithLifecycle(viewModel.detailUiState)

    OnLifecycleEvent { _, event ->
        viewModel.lifecycleStateChanged(event)
    }

    val context = LocalContext.current
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

    DetailScreen(
        modifier = modifier,
        toolbarTitle = toolbarTitle,
        viewState = viewState,
        scaffoldState = scaffoldState,
        navigateUp = navigateUp)

}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    toolbarTitle: String,
    viewState: DetailScreenUiState,
    scaffoldState: ScaffoldState,
    navigateUp: () -> Unit,
) {


    Scaffold(modifier = modifier, topBar = {
        Toolbar(title = toolbarTitle, showBackArrow = true, onNavigateUp = navigateUp)
    }, content = {

        Loading(viewState.isLoading)

        CharacterDetailView(modifier = Modifier
            .padding(it),
            uiState = viewState,
            showContent = viewState.isLoading.not())

    }, scaffoldState = scaffoldState, snackbarHost = { DefaultSnackbar(snackbarHostState = it) })

}

@Composable
fun CharacterDetailView(
    modifier: Modifier,
    uiState: DetailScreenUiState,
    showContent: Boolean,
) {

    AnimatedVisibility(visible = showContent,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))) {
        Column(modifier = modifier
            .fillMaxSize()
            .background(
                if (uiState.isDataUpToDate) MaterialTheme.colors.background else MaterialTheme.colors.error
            )
            .verticalScroll(rememberScrollState())) {

            AsyncImage(model = uiState.imageUrl,
                requestBuilder = { crossfade(true) },
                contentDescription = uiState.imageUrl,
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp))

            CharacteristicsView(modifier = Modifier, uiState = uiState)

            EpisodesView(episodes = uiState.episodes)

        }

    }
}

@Composable
fun CharacteristicsView(modifier: Modifier, uiState: DetailScreenUiState) {
    Column(Modifier
        .padding(horizontal = 15.dp)
        .padding(top = 25.dp)) {

        Text(
            text = stringResource(id = R.string.characterisc),
            style = MaterialTheme.typography.h4)

        Column(modifier.padding(start = 15.dp)) {

            CharacteristicVerticalTextView(
                modifier = Modifier.padding(top = 15.dp),
                characteristic = stringResource(id = R.string.name),
                definition = uiState.name)

            CharacteristicVerticalTextView(
                modifier = Modifier.padding(top = 5.dp),
                characteristic = stringResource(id = R.string.gender),
                definition = uiState.gender)

            CharacteristicVerticalTextView(
                modifier = Modifier.padding(top = 5.dp),
                characteristic = stringResource(id = R.string.state),
                definition = uiState.state)

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                color = Color.Gray
            )
            Row(modifier = Modifier.padding(top = 15.dp)) {
                CharacteristicColumnTextView(
                    modifier = Modifier.weight(0.5f),
                    characteristic = stringResource(id = R.string.location),
                    definition = uiState.location)

                CharacteristicColumnTextView(modifier = Modifier
                    .weight(0.5f),
                    characteristic = stringResource(id = R.string.origin),
                    definition = uiState.origin)
            }

        }
    }
}


@Composable
fun CharacteristicVerticalTextView(
    modifier: Modifier = Modifier,
    characteristic: String,
    definition: String,
) {
    definition.ifNotEmpty {
        Row(modifier = modifier) {
            Text(modifier = Modifier,
                text = characteristic,
                style = MaterialTheme.typography.h2)

            Text(modifier = Modifier
                .padding(start = 5.dp)
                .align(Alignment.Bottom),
                text = definition,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun CharacteristicColumnTextView(
    modifier: Modifier = Modifier,
    characteristic: String,
    definition: String,
) {
    definition.ifNotEmpty {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier,
                text = characteristic,
                style = MaterialTheme.typography.h2)

            Text(modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                text = definition,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start)
        }
    }
}


@Composable
fun EpisodesView(modifier: Modifier = Modifier, episodes: List<Episode>) {
    if (episodes.isEmpty().not()) {
        Column(modifier = modifier.padding(vertical = 15.dp)) {

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.h4)

            LazyRow(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(horizontal = 15.dp),
            ) {
                items(episodes, itemContent = { episode ->
                    EpisodeCard(episode)
                })
            }
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode) {

    Box(modifier = Modifier
        .width(190.dp)
        .height(100.dp)
        .padding(8.dp)
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
        .background(Color.White)
        .padding(8.dp)) {
        Text(maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = episode.name.value,
            style = MaterialTheme.typography.h3)
        Text(modifier = Modifier.align(Alignment.CenterStart), text = episode.code)
        Text(modifier = Modifier.align(Alignment.BottomStart), text = episode.date)
    }

}


@Composable
@Preview
fun MyCharacteristicTextView() {
    RickAndMortyTheme {
        CharacteristicVerticalTextView(modifier = Modifier,
            characteristic = "Name",
            definition = "Rick")
    }
}

@Composable
@Preview
fun MyCharacteristicTextView2() {
    RickAndMortyTheme {
        CharacteristicColumnTextView(modifier = Modifier,
            characteristic = "Location",
            definition = "Rick")
    }
}

@Composable
@Preview
fun MyEpisodeCard() {
    RickAndMortyTheme {
        EpisodeCard(episode = Episode(id = Id(value = 1),
            name = Name(value = "Rick goes to space"),
            date = "1031",
            code = "10F120D"))
    }
}

@Composable
@Preview
fun MyCharacteristicView() {
    RickAndMortyTheme {
        CharacteristicsView(modifier = Modifier,
            DetailScreenUiState(isLoading = false,
                isDataUpToDate = true,
                name = "Rick",
                gender = "Male",
                imageUrl = "",
                state = "Alive",
                location = "In a far far far away galaxy, 1 trillon km",
                origin = "Earth",
                episodes = listOf(
                    Episode(id = Id(value = 1),
                        name = Name(value = "Rick goes to space"),
                        date = "1031",
                        code = "10F120D"),
                    Episode(id = Id(value = 2),
                        name = Name(value = "Rick goes to space"),
                        date = "1031",
                        code = "10F120D"),
                    Episode(id = Id(value = 2),
                        name = Name(value = "Rick goes to space"),
                        date = "1031",
                        code = "10F120D"),
                )))
    }
}

@Composable
@Preview
fun MyEpisodesView() {
    RickAndMortyTheme {
        EpisodesView(
            episodes = listOf(
                Episode(id = Id(value = 1),
                    name = Name(value = "Rick goes to space"),
                    date = "1031",
                    code = "10F120D"),
                Episode(id = Id(value = 2),
                    name = Name(value = "Rick goes to space"),
                    date = "1031",
                    code = "10F120D"),
                Episode(id = Id(value = 2),
                    name = Name(value = "Rick goes to space"),
                    date = "1031",
                    code = "10F120D"),
            )
        )
    }
}

