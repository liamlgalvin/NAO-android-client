package com.example.naoandroidclient.ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun SmallLogoImage(bitmap: ImageBitmap) {
    if (bitmap == null){
        return
    }
    Image(bitmap = bitmap,
        contentDescription = "Logo",
        modifier =  Modifier
            .height(70.dp)
            .width(70.dp)
            .padding(horizontal = 5.dp))
}
