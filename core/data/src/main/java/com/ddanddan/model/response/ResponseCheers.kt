package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseCheers(
    val cheerId: String,
    val cheereeId: String,
    val date: String,
    val createdAt: String
)