package com.example.naoandroidclient.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Immutable
data class BackgroundTheme(
    val color: Color = Color.DarkGray,
    val tonalElevation: Dp = Dp.Hairline,
    val primaryGradientColor: Color = Color.Gray,
    val secondaryGradientColor: Color = Color.DarkGray,
    val tertiaryGradientColor: Color = Color.DarkGray,
    val neutralGradientColor: Color = Color.DarkGray
)

val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }