package com.sample.feedpresentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


internal val blue200 = Color(0xFF23B8CC)
internal val grey = Color(0xFFF9F9F9)
internal val grey400 = Color(0xFF7F7F7F)
internal val grey600 = Color(0xFF4A4A4A)
internal val red = Color(0xFFF68A94)


val LightColors = lightColors(
    primary = blue200,
    secondary = Color.White,
    background = grey,
    surface = Color.White,
    error = red
)

val DarkColors = darkColors(
    primary = blue200,
    secondary = Color.Black,
    background = grey,
    surface = Color.Black,
    error = red
)