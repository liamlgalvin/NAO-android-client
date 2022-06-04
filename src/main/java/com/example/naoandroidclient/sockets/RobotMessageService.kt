package com.example.naoandroidclient.sockets

import com.example.naoandroidclient.sockets.dto.Apps
import com.example.naoandroidclient.sockets.dto.Message
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface RobotMessageService {
    @Receive
    fun observeWebSocket(): Flow<WebSocket.Event>

    @Send
    fun sendMessage(message: Message)

    @Receive
    fun observeMessage(): Flow<Message>

    @Receive
    fun observeApps(): Flow<Apps>
}