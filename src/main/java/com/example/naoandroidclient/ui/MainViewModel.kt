package com.example.naoandroidclient.ui


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.naoandroidclient.domain.ActivityNotification
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.domain.RobotStatus
import com.example.naoandroidclient.sockets.FlowStreamAdapter
import com.example.naoandroidclient.sockets.RobotMessageService
import com.example.naoandroidclient.sockets.dto.Message
import com.example.naoandroidclient.sockets.dto.Subscribe
import com.example.naoandroidclient.sockets.mapper.RobotStatusMapper
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
class MainViewModel @Inject constructor(
    state: SavedStateHandle,
    private var client: OkHttpClient,
    private var moshi: Moshi,
    private var lifecycle: Lifecycle,
    private var appRepository: InMemoryAppRepository,
): ViewModel() {

    private val robotStatusMapper: RobotStatusMapper = RobotStatusMapper()
    lateinit var webSocketService: RobotMessageService

    var robotStatus =  state.getLiveData<RobotStatus>("robotStatus" , RobotStatus.NO_APP_RUNNING)
    var message =  state.getLiveData<String>("message")
    var errorMessage =  state.getLiveData<String>("errorMessage")
    var currentApp =  state.getLiveData<App>("currentApp")

    var connectedState = state.getLiveData<String>("connectedState")
    var previousConnectedState = mutableStateOf("")

    val activityNotification: MutableLiveData<ActivityNotification> by lazy { MutableLiveData<ActivityNotification>() }

    var showProgressBar = MutableLiveData(false)
    val connectionStatus = MutableLiveData(ConnectionStatus.NOT_CONNECTED)

    fun getConnectedState() : String? {
        previousConnectedState.value = connectedState.value.toString()
        return connectedState.value
    }

    fun sendMessage(type: String) {
        sendMessage(type, "")
    }

    fun sendMessage(type: String, message: String) {
        webSocketService.sendMessage(Message(type, "", "", message))
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
                handleMessage(message)
            }
           .launchIn(viewModelScope)

        webSocketService.observeApps()
            .flowOn(Dispatchers.IO)
            .onEach { message ->
                // fixme:
                appRepository.apps.removeAll(appRepository.apps)
                appRepository.apps.addAll(appRepository.appMapper.map(message.apps))
            }
            .launchIn(viewModelScope)
    }

    private fun handleMessage(message: Message) {
        // todo
        when (message.type) {
            "error" -> {
                println("ERROR!!! ${message.message}")
                this.message.value = message.message
            }
            else -> this.message.value = message.message
        }
        println(message.robotStatus)
        this.robotStatus.value = robotStatusMapper.map(message.robotStatus)
        this.currentApp.value = if (message.currentAppId != "") appRepository.getAppById(message.currentAppId.toLong()) else App(0,"","","")
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

    fun completeWebSocketConnection() {
        this.connectedState.value = "connected to robot" // todo fix this
        toggleConnectionStatus()
        toggleProgressBar()
        webSocketService.sendSubscribe(Subscribe()) //fixme: necessary??
        sendMessage("get_apps")
    }

    fun disconnectWebSocket() {
        if (connectionStatus.value == ConnectionStatus.CONNECTED) {
            this.connectedState.value ="connection lost" // todo fix this
        } else {
            this.connectedState.value ="connection failed: check ip" // todo fix this
        }
        toggleConnectionStatus()
        toggleProgressBar()
        destroyWebSocket()
        activityNotification.value = ActivityNotification.RESTART
    }

    fun createRobotMessageService(ip: String) {
        val scarlet = Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("ws://${ip}:8001"))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(lifecycle)
            .build()

        webSocketService = scarlet.create()
    }

    fun sendCreateNotification() {
        activityNotification.value = ActivityNotification.CREATE_CONNECTION
    }
    fun sendObserveNotification() {
        activityNotification.value = ActivityNotification.OBSERVE
    }

    fun destroyWebSocket() {
        client.dispatcher.executorService.shutdown()
    }

    fun toggleProgressBar() {
        showProgressBar.value = !showProgressBar.value!!
    }

    private fun toggleConnectionStatus() {
        connectionStatus.value = when (connectionStatus.value) {
            ConnectionStatus.CONNECTED -> ConnectionStatus.NOT_CONNECTED
            ConnectionStatus.NOT_CONNECTED -> ConnectionStatus.CONNECTED
            else -> {ConnectionStatus.NOT_CONNECTED}
        }
    }

    // sending message stuff

    @OptIn(ExperimentalStdlibApi::class)
    fun jsonifyApp(appToConvert: App): String {
        val jsonAdapter: JsonAdapter<com.example.naoandroidclient.sockets.dto.App> = moshi.adapter()
        return jsonAdapter.toJson(appRepository.appMapper.map(appToConvert))
    }
}