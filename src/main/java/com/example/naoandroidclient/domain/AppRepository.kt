package com.example.naoandroidclient.domain

interface AppRepository {

    fun getAppsAlphabetisedGroupedFiltered(searchText: String) : Map<Char, List<App>>
    fun getAppsAlphabetisedGrouped(): Map<Char, List<App>>
    fun getApps() : List<App>
    fun getAppById(appId: Long) : App?

}