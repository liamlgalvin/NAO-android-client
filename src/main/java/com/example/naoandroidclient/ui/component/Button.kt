package com.example.naoandroidclient.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonText (text : String) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.button,
        modifier = Modifier.padding(10.dp)
    )
}