package com.example.naoandroidclient

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.naoandroidclient.domain.ActivityNotification
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.main.MainScreen
import com.example.naoandroidclient.ui.main.NaoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (savedInstanceState != null) {
//            // get ip if it has been set on closing
//            savedInstanceState.getString("ip")?.let { mainViewModel.setIp(it) }
//            savedInstanceState.getString("connectedState")?.let { mainViewModel.setConnectedState(it) }
//        }

        val activityNotificationObserver = createActivityNotificationObserver()
        mainViewModel.activityNotification.observe(this, activityNotificationObserver)

        setContent {
            NaoApp {
                MainScreen(mainViewModel = mainViewModel)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // move this to a function in view model
        if (mainViewModel.connectionStatus.value == ConnectionStatus.CONNECTED) {
            mainViewModel.sendMessage("destroy_connection", "connection destroyed")
        }
        mainViewModel.destroyWebSocket()
    }

    private fun createActivityNotificationObserver(): Observer<ActivityNotification> {
        return Observer<ActivityNotification> {
            when(it){
                ActivityNotification.RESTART -> {
                    val intent = createIntent()
                    finish()
                    startActivity(intent)
                }
                ActivityNotification.OBSERVE -> {
                    mainViewModel.observeConnection()
                }
                else -> {}
            }

        }
    }

    private fun createIntent(): Intent {
        val intent = intent
        intent.putExtras(createBundle())
        return intent
    }

    private fun createBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("ip", mainViewModel.ip.value)
        bundle.putString("connectedState",  mainViewModel.connectedState.value )
        return bundle
    }


}
