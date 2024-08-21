package com.ddanddan.data.service

import com.ddanddan.model.response.ResponseGetSample
import retrofit2.http.GET

interface SampleService {
    @GET("/endpoint")
    suspend fun getSample(
        ): ResponseGetSample
}