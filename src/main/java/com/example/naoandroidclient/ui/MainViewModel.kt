package com.example.naoandroidclient.ui



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.naoandroidclient.R
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import com.example.naoandroidclient.domain.ActivityNotification
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.domain.RobotStatus
import com.example.naoandroidclient.sockets.FlowStreamAdapter
import com.example.naoandroidclient.sockets.RobotMessageService
import com.example.naoandroidclient.sockets.dto.Message
import com.example.naoandroidclient.sockets.mapper.ErrorMessageMapper
import com.example.naoandroidclient.sockets.mapper.MessageMapper
import com.example.naoandroidclient.sockets.mapper.RobotStatusMapper
import com.squareup.moshi.Moshi
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
    var robotIp = mutableStateOf("")

    var robotStatus =  state.getLiveData<RobotStatus>("robotStatus" , RobotStatus.NO_APP_RUNNING)
    var message =  state.getLiveData<Int>("message")
    var errorMessage =  state.getLiveData<Int>("errorMessage")
    var currentApp =  state.getLiveData<App>("currentApp")

    var connectedState = state.getLiveData<Int>("connectedState")
    var previousConnectedState = mutableStateOf(0)

    val activityNotification: MutableLiveData<ActivityNotification> by lazy { MutableLiveData<ActivityNotification>() }

    var showProgressBar = MutableLiveData(false)
    val connectionStatus = MutableLiveData(ConnectionStatus.NOT_CONNECTED)

    fun getConnectedState() : Int? {
        previousConnectedState.value = connectedState.value!!
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
        val messageMapper = MessageMapper()
        val errorMessageMapper = ErrorMessageMapper()

        when (message.type) {
            "error" -> {
                this.errorMessage.value = errorMessageMapper.map(message.message)
            }
            else -> this.message.value = messageMapper.map(message.message)
        }
        this.robotStatus.value = robotStatusMapper.map(message.robotStatus)
        this.currentApp.value = if (message.currentAppId != "") appRepository.getAppById(message.currentAppId.toLong()) else defaultApp()
    }

    private fun defaultApp() = App(0,"","","")

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
        this.connectedState.value = R.string.connected_to_robot
        toggleConnectionStatus()
        toggleProgressBar()
        sendMessage("get_apps")
    }

    fun disconnectWebSocket() {
        if (connectionStatus.value == ConnectionStatus.CONNECTED) {
            this.connectedState.value = R.string.connection_lost
        } else {
            this.connectedState.value = R.string.check_ip
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

    fun sendCreateConnectionNotification() {
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

}