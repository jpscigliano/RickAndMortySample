package com.sample.feedpresentation.theme


import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sample.feedpresentation.R

val RicksFont = FontFamily(
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_medium, FontWeight.W500),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val RickAndMortyTypography = Typography(
    h1 = TextStyle(
        fontFamily = RicksFont,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        color = Color.White,
        lineHeight = 32.sp
    ),
    h2 = TextStyle(
        fontFamily = RicksFont,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        color = grey600,
        lineHeight = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = RicksFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        color = grey400,
        lineHeight = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = RicksFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = grey400,
        lineHeight = 24.sp
    ),
    button = TextStyle(
        fontFamily = RicksFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp

    )

)
