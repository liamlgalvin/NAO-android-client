package com.example.naoandroidclient.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import com.example.naoandroidclient.data.repository.InMemoryDefaultAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var appRepository: InMemoryAppRepository,
    private var defaultAppRepository: InMemoryDefaultAppRepository
    ): ViewModel() {

    var robotIp = mutableStateOf("")

    var tabIndex = mutableStateOf(0)

    fun getAllAppsGrouped() = appRepository.getAppsAlphabetisedGrouped()

    fun getTopApps() = appRepository.getApps().shuffled().take(4)

    fun getDefaultApps() = defaultAppRepository.getApps()
    }
