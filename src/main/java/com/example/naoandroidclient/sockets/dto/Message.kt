package com.example.naoandroidclient.sockets.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val type: String,
    val robotStatus: String, //create enum map this
    val currentAppId: String, // map this if current
    val message: String
    )
