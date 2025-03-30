package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestUserSetting(
    val isAppPushOn: Boolean
)