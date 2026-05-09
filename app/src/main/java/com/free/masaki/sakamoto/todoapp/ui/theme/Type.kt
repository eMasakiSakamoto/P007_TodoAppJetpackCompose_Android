package com.free.masaki.sakamoto.todoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.free.masaki.sakamoto.todoapp.R

val HachiMaruPop = FontFamily(
    Font(R.font.hachi_maru_pop_regular, FontWeight.Normal)
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 30.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineMedium = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 26.sp,
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal
    ),
    titleMedium = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyLarge = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = HachiMaruPop,
        fontSize = 14.sp
    )
)
