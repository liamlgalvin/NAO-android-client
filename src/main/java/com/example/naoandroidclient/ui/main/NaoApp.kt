package com.example.naoandroidclient.ui.main

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.naoandroidclient.ui.theme.NaoAppTheme

@Composable
fun NaoApp(content: @Composable () -> Unit) {
    NaoAppTheme(){
        Surface() {
            content()
        }
    }

}