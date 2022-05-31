package com.example.naoandroidclient.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var appRepository: InMemoryAppRepository
    ): ViewModel() {

    var tabIndex = mutableStateOf(0)

    fun getAllAppsGrouped() = appRepository.getAppsAlphabetisedGrouped()

    fun getTopApps() = appRepository.getApps()
    }
