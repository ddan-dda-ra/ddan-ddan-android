package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.RankService
import com.ddanddan.model.response.ResponseRanking
import javax.inject.Inject

class RemoteRankDataSource @Inject constructor(
    private val rankService: RankService
) {
    suspend fun getRanking(criteria: String): ResponseRanking =
        rankService.getRanking(criteria, "MONTHLY")
}