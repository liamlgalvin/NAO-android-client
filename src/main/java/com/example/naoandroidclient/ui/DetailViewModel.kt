package com.example.naoandroidclient.ui


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.*
import com.example.naoandroidclient.domain.ActivityNotification
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.sockets.FlowStreamAdapter
import com.example.naoandroidclient.sockets.mapper.AppMapper
import com.example.naoandroidclient.sockets.RobotMessageService
import com.example.naoandroidclient.sockets.dto.Message
import com.example.naoandroidclient.sockets.dto.Subscribe
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private var client: OkHttpClient,
    private var moshi: Moshi,
    private var lifecycle: Lifecycle,
): ViewModel() {

    private lateinit var webSocketService: RobotMessageService

    // todo move
    private val appMapper = AppMapper()
    var apps = SnapshotStateList<App>()

    // TODO: RepositoryListener...


    var ip =  mutableStateOf(state["ip"] ?: "")
    var message =  mutableStateOf("")


    var connectedState =  mutableStateOf("connecting...") // todo some sort of flow data etc
    val activityNotification: MutableLiveData<ActivityNotification> by lazy { MutableLiveData<ActivityNotification>() }


    val connectionStatus = MutableLiveData(ConnectionStatus.NOT_CONNECTED)


    fun setIp(ip : String) {
        this.ip.value = ip
        state["ip"] = ip
    }

    fun isValidIp(): Boolean {
        if (ip.value == "") return false
        val regex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$")
        return regex.matches(ip.value)
    }

    fun updateMessage(message: String) {
        this.message.value = message
    }

    fun sendMessage(type: String) {
        sendMessage(type, "")
    }

    fun sendMessage(type: String, message: String) {
        webSocketService.sendMessage(Message(type, message))
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun jsonifyApp(appToConvert: App): String {
        val jsonAdapter: JsonAdapter<com.example.naoandroidclient.sockets.dto.App> = moshi.adapter()
        return jsonAdapter.toJson(appMapper.map(appToConvert))
    }


    fun observeConnection() {
        webSocketService.observeWebSocket()
            .flowOn(Dispatchers.IO)
            .onEach { event ->
                onReceiveResponseConnection(event)
            }
            .launchIn(viewModelScope)

        webSocketService.observeMessage()
            .flowOn(Dispatchers.IO)
            .onEach { message ->
                updateMessage(message.message)
            }
            .launchIn(viewModelScope)

        webSocketService.observeApps()
            .flowOn(Dispatchers.IO)
            .onEach { message ->
                this.apps.removeAll(apps)
                this.apps.addAll(appMapper.map(message.apps))
            }
            .launchIn(viewModelScope)


    }

    private fun onReceiveResponseConnection(response: WebSocket.Event) {
        when (response) {
            is WebSocket.Event.OnConnectionOpened<*> -> {
                completeWebSocketConnection()
            }
            is WebSocket.Event.OnConnectionFailed -> {
                disconnectWebSocket()
            }
            else -> {}
        }
    }

    private fun completeWebSocketConnection() {
        connectedState.value = "connected" // todo fix this
        toggleConnectionStatus()
        webSocketService.sendSubscribe(Subscribe())
        sendMessage("get_apps")
        sendMessage("get_status")
    }

    private fun disconnectWebSocket() {
        connectedState.value = "connection failed" // todo fix this
        toggleConnectionStatus()
        destroyWebSocket()
        activityNotification.value = ActivityNotification.RESTART
    }

    fun createRobotMessageService() {
        val scarlet = Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("ws://${ip.value}:8001"))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(lifecycle)
            .build()

        webSocketService = scarlet.create()
    }

    fun sendObserveNotification() {
        activityNotification.value = ActivityNotification.OBSERVE
    }

    fun destroyWebSocket() {
        client.dispatcher.executorService.shutdown()
    }

    private fun toggleConnectionStatus() {
        connectionStatus.value = when (connectionStatus.value) {
            ConnectionStatus.CONNECTED -> ConnectionStatus.NOT_CONNECTED
            ConnectionStatus.NOT_CONNECTED -> ConnectionStatus.CONNECTED
            else -> {
                ConnectionStatus.NOT_CONNECTED}
        }
    }

}