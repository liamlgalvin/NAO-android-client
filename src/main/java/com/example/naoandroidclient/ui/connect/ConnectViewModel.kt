package com.example.naoandroidclient.ui.connect

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(private val state: SavedStateHandle): ViewModel() {

    var ip =  mutableStateOf(state["ip"] ?: "")

    fun setIp(ip : String) {
        this.ip.value = ip
        state["ip"] = ip
    }

    fun isValidIp(ip: String): Boolean {
        if (ip == "") return false
        val regex = Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$")
        return regex.matches(ip)
    }

}