package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseInviteCode(
    val code: String,
    val expiresAt: String,
    val createdAt: String
)