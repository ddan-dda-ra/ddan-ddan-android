package com.ddanddan.data.datasource.remote

import com.ddanddan.model.response.ResponseGetSample
import com.ddanddan.data.service.SampleService
import javax.inject.Inject

class RemoteSampleDataSource @Inject constructor(private val sampleService: SampleService) {
    suspend fun getRecommendCourse(pageNo: String?): ResponseGetSample =
        sampleService.getSample()
}