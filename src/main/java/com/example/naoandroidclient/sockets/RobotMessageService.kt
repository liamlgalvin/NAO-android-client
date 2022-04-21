package com.example.naoandroidclient.sockets

import com.example.naoandroidclient.sockets.dto.Message
import com.example.naoandroidclient.sockets.dto.Subscribe
import com.squareup.moshi.JsonClass
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

@JsonClass(generateAdapter = true)
interface RobotMessageService {
    @Receive
    fun observeWebSocket(): Flow<WebSocket.Event>

    @Send
    fun sendSubscribe(subscribe: Subscribe)

    @Receive
    fun observeMessage(): Flow<Message>
}