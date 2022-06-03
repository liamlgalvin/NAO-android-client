package com.example.naoandroidclient.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

@Composable
fun AppTextLogo(modifier: Modifier = Modifier, color: Color) {
    Text(
        text = "N\u0245O",
        style = MaterialTheme.typography.h1,
        fontFamily = FontFamily.SansSerif,
        modifier = modifier,
        color = color
    )
}