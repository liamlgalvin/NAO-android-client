package com.example.naoandroidclient.ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun SmallLogoImage(bitmap: ImageBitmap, modifier: Modifier = Modifier) {
    if (bitmap == null){
        return
    }
    Image(bitmap = bitmap,
        contentDescription = "Logo",
        modifier =  modifier
            .height(70.dp)
            .width(70.dp)
    )
}

@Composable
fun MediumLogoImage(bitmap: ImageBitmap,
                   modifier: Modifier = Modifier) {
    if (bitmap == null){
        return
    }
    Image(bitmap = bitmap,
        contentDescription = "Logo",
        modifier =  modifier
            .clip(RoundedCornerShape(20.dp))
            .height(100.dp)
            .width(100.dp)
    )
}

@Composable
fun LargeLogoImage(bitmap: ImageBitmap,
                    modifier: Modifier = Modifier) {
    if (bitmap == null){
        return
    }
    Image(bitmap = bitmap,
        contentDescription = "Logo",
    modifier =  modifier
        .clip(RoundedCornerShape(20.dp))
            .height(200.dp)
            .width(200.dp)
            .padding(horizontal = 0.dp)

    )
}


@Composable
fun HeaderLogoImage(bitmap: ImageBitmap,
                   modifier: Modifier = Modifier) {
    if (bitmap == null){
        return
    }
    Image(bitmap = bitmap,
        contentDescription = "Logo",
        modifier =  modifier
            .height(250.dp)
            .width(250.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 0.dp)

    )
}


