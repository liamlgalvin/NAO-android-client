package com.example.naoandroidclient.data.repository

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.sockets.mapper.AppMapper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class AppRepository (
    private val moshi: Moshi,
    val appMapper: AppMapper
) {

    var apps = SnapshotStateList<App>()
    var searchText =  mutableStateOf("")

    fun getAppsGrouped() = apps.toList().filter { app ->
        app.name.contains(searchText.value)
    }.groupBy { app -> app.name[0] }

    @OptIn(ExperimentalStdlibApi::class) // doesnt go here
    fun jsonifyApp(appToConvert: App): String {
        val jsonAdapter: JsonAdapter<com.example.naoandroidclient.sockets.dto.App> = moshi.adapter()
        return jsonAdapter.toJson(appMapper.map(appToConvert))
    }

}