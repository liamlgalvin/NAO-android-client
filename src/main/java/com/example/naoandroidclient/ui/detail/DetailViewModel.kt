package com.example.naoandroidclient.ui.detail

import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var appRepository: InMemoryAppRepository
): ViewModel() {

    fun getAppById(appId: Long) = appRepository.getAppById(appId)

}