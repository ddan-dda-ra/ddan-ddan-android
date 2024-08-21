package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteSampleDataSource
import com.ddanddan.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(private val remoteSampleDataSource: RemoteSampleDataSource) :
    SampleRepository {
    override suspend fun getSample(): MutableList<Any> {
        TODO("Not yet implemented")
    }

}