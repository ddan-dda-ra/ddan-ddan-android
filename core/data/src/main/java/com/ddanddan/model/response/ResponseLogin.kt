package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLogin (
    val accessToken: String,
    val refreshToken: String,
    val user: ResponseUser?,
    val isOnboardingComplete: Boolean
)