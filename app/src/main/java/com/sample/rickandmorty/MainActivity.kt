@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.sample.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sample.feedpresentation.theme.RickAndMortyTheme

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberAnimatedNavController()
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier,
                    //scaffoldState = rememberScaffoldState(),
                    snackbarHost = {
                        //  SnackbarHost(it)
                    },
                    content = {
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(it),
                            widthSizeClass = widthSizeClass
                        )
                    }
                )
            }
        }
    }
}