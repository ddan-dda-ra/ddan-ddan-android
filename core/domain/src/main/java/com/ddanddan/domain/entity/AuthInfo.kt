package com.ddanddan.domain.entity

data class AuthInfo (
    val accessToken: String,
    val refreshToken: String,
    val isOnboardingComplete: Boolean
)