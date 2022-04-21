package com.example.naoandroidclient.sockets.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscribe(
    val type: String = "subscribe"
)
