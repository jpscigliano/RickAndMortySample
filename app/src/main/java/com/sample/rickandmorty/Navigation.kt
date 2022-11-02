@file:OptIn(ExperimentalAnimationApi::class)

package com.sample.rickandmorty

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sample.feedpresentation.characterDetail.DetailScreen
import com.sample.feedpresentation.characterList.ListScreen

internal sealed class Screen(val route: String) {
    object Feed : Screen("Home")
}

private sealed class LeafScreen(val leafRoute: String) {
    fun createRoute(root: Screen) = "${root.route}/$leafRoute"

    object CharacterList : LeafScreen(leafRoute = "characterList")

    object CharacterDetail : LeafScreen(leafRoute = "characterList/{characterId}?{characterName}") {
        fun createRoute(root: Screen, characterId: Int, characterName: String) =
            "${root.route}/characterList/$characterId?$characterName"
    }

    //TODO
    object CharacterListWithDetail : LeafScreen(leafRoute = "characterListWithDetail")
}


@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Feed.route,
        modifier = modifier,
    ) {
        addFeedTopLevel(navController, widthSizeClass)
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addFeedTopLevel(
    navController: NavController,
    widthSizeClass: WindowWidthSizeClass,
) {
    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

    val startDestination = if (isExpandedScreen)
        LeafScreen.CharacterListWithDetail.createRoute(Screen.Feed)
    else
        LeafScreen.CharacterList.createRoute(Screen.Feed)

    navigation(
        route = Screen.Feed.route,
        startDestination = startDestination,
    ) {
        addCharacterList(navController, Screen.Feed)
        addCharacterDetail(navController, Screen.Feed)
    }
}

private fun NavGraphBuilder.addCharacterList(
    navController: NavController,
    root: Screen,
) {
    composable(route = LeafScreen.CharacterList.createRoute(root)) {
        ListScreen(
            onCharacterSelected = { character ->
                navController.navigate(LeafScreen.CharacterDetail.createRoute(root,
                    character.id.value,
                    character.name?.value ?: ""))
            }
        )
    }
}

private fun NavGraphBuilder.addCharacterDetail(
    navController: NavController,
    root: Screen,
) {
    composable(route = LeafScreen.CharacterDetail.createRoute(root)) { backStackEntry ->
        val name: String = backStackEntry.arguments?.getString("characterName") ?: ""
        DetailScreen(toolbarTitle = name,
            navigateUp = navController::navigateUp,
            showNavigateUp = true)
    }
}

