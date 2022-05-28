package com.example.naoandroidclient.ui.home

import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var appRepository: InMemoryAppRepository
    ): ViewModel() {

    fun getAllAppsGrouped() = appRepository.getAppsAlphabetisedGrouped()
    }
