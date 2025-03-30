package com.ddanddan.model.response

import com.ddanddan.domain.entity.UserSetting
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserSetting(
    val isAppPushOn: Boolean
) {
    fun toUserSetting(): UserSetting = UserSetting(isAppPushOn)
}