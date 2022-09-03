package com.example.naoandroidclient.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.NordicWalking
import com.example.naoandroidclient.domain.DefaultApp
import com.example.naoandroidclient.domain.DefaultAppRepository
import com.example.naoandroidclient.ui.navigation.Screen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class InMemoryDefaultAppRepository @Inject constructor(
): DefaultAppRepository {

    private var defaultApps = mapOf(
        1L to DefaultApp(1,"Speak","", Icons.Default.ChatBubble, Screen.SpeakScreen.route), // todo: make the name a string
        2L to DefaultApp(2,"Walk","", Icons.Default.NordicWalking, Screen.WalkScreen.route),
        3L to DefaultApp(3,"Rest/Wake","",Icons.Default.ModeNight, Screen.RestScreen.route),
        )

    override fun getApps(): List<DefaultApp> {
        return defaultApps.values.toList()
    }

    override fun getAppById(appId: Long): DefaultApp? {
        return defaultApps[appId]
    }

}