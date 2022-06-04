package com.example.naoandroidclient.sockets.mapper

import com.example.naoandroidclient.R

class ErrorMessageMapper {

    private val messageMap = mapOf(
        "APP_DOESNT_EXIST" to R.string.app_doesnt_exist, //"app doesn't exist"
        "APP_LOCATION_INCORRECT" to R.string.app_location_incorrect,
        "APP_ALREADY_RUNNNG" to R.string.app_already_runnng,
        "PROBLEM_RUNNING_APP" to R.string.problem_running_app,
        "NO_APP_RUNNING" to R.string.no_app_running,
        "COULD_NOT_CANCEL_APP" to R.string.could_not_cancel_app,
        "UNKNOWN_ERROR" to R.string.unknown_error,
    ).withDefault {R.string.unknown_error}

    fun map (message: String) = messageMap[message]
}