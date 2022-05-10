package com.example.naoandroidclient.ui.main

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun NaoApp(content: @Composable () -> Unit) {
    // todo: add a  theme...
    Surface() {
        content()
    }
}