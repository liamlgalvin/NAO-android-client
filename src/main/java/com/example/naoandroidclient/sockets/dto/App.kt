package com.example.naoandroidclient.sockets.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class App(
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
)
