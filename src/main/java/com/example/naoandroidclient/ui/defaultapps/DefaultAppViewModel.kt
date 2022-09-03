package com.example.naoandroidclient.ui.defaultapps


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DefaultAppViewModel @Inject constructor(state: SavedStateHandle): ViewModel() {
    val message = state.getLiveData("message", "")
}