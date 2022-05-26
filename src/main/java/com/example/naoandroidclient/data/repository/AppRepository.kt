package com.example.naoandroidclient.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.domain.AppRepository
import com.example.naoandroidclient.sockets.mapper.AppMapper
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Singleton
@ActivityScoped
class AppRepository (
    val appMapper: AppMapper
) : AppRepository {

    var apps = SnapshotStateList<App>()

    override fun getAppsAlphabetisedGroupedFiltered(searchText: String): Map<Char, List<App>> {

        val searchTextWords = searchText.lowercase().split(" ").toSet()
        var foundApps = mutableSetOf<App>()

        for (app in apps){
            if (allWordsInName(app.name, searchTextWords)) {
                foundApps.add(app)
            }
        }
        return foundApps.groupBy { app -> app.name[0] }
    }

    private fun allWordsInName(appName: String, searchTextWords: Set<String>): Boolean {
        for (word in searchTextWords){
            if (!appName.contains(word)) return false
        }
        return true
    }

    override fun getAppsAlphabetisedGrouped() = apps.toList().groupBy { app -> app.name[0] }

    override fun getApps()  = apps.toList()

    override fun getAppById(appId: Long) = apps.find { it.id == appId }

}