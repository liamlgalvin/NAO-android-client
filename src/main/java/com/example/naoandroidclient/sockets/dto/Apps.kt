package com.example.naoandroidclient.sockets.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Apps(
    val apps: List<App>
)
