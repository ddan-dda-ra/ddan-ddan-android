package com.ddanddan.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRanking(
    val criteria: String,
    val periodType: String,
    val ranking: List<ResponseRank>,
    val myRanking: ResponseRank
)