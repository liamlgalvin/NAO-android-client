package com.example.naoandroidclient.domain

import androidx.compose.ui.graphics.vector.ImageVector

class DefaultApp(
    val id: Long,
    val name: String,
    val description: String,
    val image: ImageVector,
    val ScreenRoute: String
) {


    fun getShortDescription(): String {
        if (this.description.length > 99)
           return "${this.description.take(100)}..."

        return this.description
    }
}