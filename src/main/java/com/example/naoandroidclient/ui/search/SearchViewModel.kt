package com.example.naoandroidclient.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.data.repository.InMemoryAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private var appRepository: InMemoryAppRepository
): ViewModel() {

    var searchText  = mutableStateOf("")

    fun getAppsGroupedFiltered(searchText: String) = appRepository.getAppsAlphabetisedGroupedFiltered(searchText)

    }
