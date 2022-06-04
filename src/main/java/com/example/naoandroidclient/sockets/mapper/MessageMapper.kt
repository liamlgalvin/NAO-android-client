package com.example.naoandroidclient.sockets.mapper

import com.example.naoandroidclient.R

class MessageMapper {
    private val messageMap = mapOf(
        "APP_STARTED" to R.string.app_started,
        "APP_FINISHED" to R.string.app_finished,
        "APP_CANCELLED" to R.string.app_cancelled,
    )

    fun map (message: String) = messageMap[message]
}