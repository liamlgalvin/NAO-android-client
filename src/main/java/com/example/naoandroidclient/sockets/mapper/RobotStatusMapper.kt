package com.example.naoandroidclient.sockets.mapper

import com.example.naoandroidclient.domain.RobotStatus

class RobotStatusMapper {
    private val robotStatusMap = mapOf(
        "APP_RUNNING" to RobotStatus.APP_RUNNING,
        "NO_APP_RUNNING" to RobotStatus.NO_APP_RUNNING
    )

    fun map(robotStatus: String) = robotStatusMap[robotStatus]
}