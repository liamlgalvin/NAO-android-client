package com.example.naoandroidclient.sockets.mapper

import com.example.naoandroidclient.domain.App


class AppMapper {

    fun map(apps: List<com.example.naoandroidclient.sockets.dto.App>) =
        apps.map{app -> map(app)}.toList()

    fun map(app: com.example.naoandroidclient.sockets.dto.App) = App(
        id = app.id,
        name = app.name,
        description = app.description,
        image = app.image
    )

    fun map(app: App) = com.example.naoandroidclient.sockets.dto.App(
        id = app.id,
        name = app.name,
        description = app.description,
        image = app.image
    )
}