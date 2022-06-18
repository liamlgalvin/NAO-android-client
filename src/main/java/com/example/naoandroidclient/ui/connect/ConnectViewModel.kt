package com.example.naoandroidclient.ui.connect

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(state: SavedStateHandle): ViewModel() {

    var ip =  state.getLiveData("ip", "")

    fun isValidIp(ip: String): Boolean {
        if (ip == "") return false
        val regex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$")
        return regex.matches(ip)
    }

}