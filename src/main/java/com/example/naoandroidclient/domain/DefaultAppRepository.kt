package com.example.naoandroidclient.domain

interface DefaultAppRepository {

    fun getApps() : List<DefaultApp>
    fun getAppById(appId: Long) : DefaultApp?

}