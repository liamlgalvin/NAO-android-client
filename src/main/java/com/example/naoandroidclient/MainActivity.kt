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
import com.example.naoandroidclient.ui.component.AppBackground
import com.example.naoandroidclient.ui.connect.ConnectViewModel
import com.example.naoandroidclient.ui.detail.DetailViewModel
import com.example.naoandroidclient.ui.home.HomeViewModel
import com.example.naoandroidclient.ui.main.MainScreen
import com.example.naoandroidclient.ui.main.NaoApp
import com.example.naoandroidclient.ui.main.topbar.MainAppBarViewModel
import com.example.naoandroidclient.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    private val mainViewModel : MainViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val connectViewModel: ConnectViewModel by viewModels()
    private val mainAppBarViewModel: MainAppBarViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityNotificationObserver = createActivityNotificationObserver()
        val robotIp = updateRobotIp()
        connectViewModel.ip.observe(this, robotIp)

        mainViewModel.activityNotification.observe(this, activityNotificationObserver)


        setContent {
            NaoApp {
                AppBackground() {
                    MainScreen(mainViewModel = mainViewModel,
                        searchViewModel = searchViewModel,
                        connectViewModel = connectViewModel,
                        mainAppBarViewModel = mainAppBarViewModel,
                        homeViewModel = homeViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }

    }

    private fun updateRobotIp(): Observer<String> {
        return Observer<String> { ip ->
            when(ip){
                else -> {
                    homeViewModel.robotIp.value = ip
                    mainViewModel.robotIp.value = ip
                }
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
        //intent.putExtras(createBundle())
        return intent
    }

//    private fun createBundle(): Bundle {
//        val bundle = Bundle()
//        bundle.putString("ip", mainViewModel.ip.value)
//        bundle.putString("connectedState",  mainViewModel.connectedState.value )
//        return bundle
//    }


}
