package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin (
    val token: String,
    val tokenType: String
)