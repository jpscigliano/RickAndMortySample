package com.sample.feedpresentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RickAndMortyTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkColors) DarkColors else LightColors,
        typography = RickAndMortyTypography,
        content = {
            rememberSystemUiController().setStatusBarColor(
                color = MaterialTheme.colors.primary
            )
            rememberSystemUiController().setNavigationBarColor(
                color = MaterialTheme.colors.secondary
            )
            content()
        }
    )
}

val Shapes.button: Shape
    get() = RoundedCornerShape(percent = 100)






