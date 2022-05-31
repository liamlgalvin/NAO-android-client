package com.example.naoandroidclient.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class App(val id: Long,
          val name: String,
          val description: String,
          val image: String) {

    fun getImageBitmap(): ImageBitmap? {
        if (this.image.isNullOrEmpty()) return null

        val decodedByte = Base64.decode(this.image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        return bitmap.asImageBitmap()
    }

    fun getShortDescription(): String {
        if (this.description.length > 99)
           return "${this.description.take(100)}..."

        return this.description
    }
}