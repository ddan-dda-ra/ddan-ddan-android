package com.ddanddan.data.service

import com.ddanddan.model.response.ResponseRanking
import retrofit2.http.GET
import retrofit2.http.Query

interface RankService {
    @GET("/v1/ranking")
    suspend fun getRanking(
        @Query("criteria") criteria: String,
        @Query("periodType") periodType: String
    ): ResponseRanking
}