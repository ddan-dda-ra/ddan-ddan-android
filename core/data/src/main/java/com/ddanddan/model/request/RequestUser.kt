package com.ddanddan.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestUser (
    val name: String,
    val purposeCalorie: Int
)