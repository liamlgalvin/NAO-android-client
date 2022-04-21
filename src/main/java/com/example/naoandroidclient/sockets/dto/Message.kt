package com.example.naoandroidclient.sockets.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val type: String,
    val message: String
)
