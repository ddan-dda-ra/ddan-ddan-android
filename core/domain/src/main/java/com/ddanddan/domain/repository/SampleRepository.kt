package com.ddanddan.domain.repository

interface SampleRepository {
    suspend fun getSample(): MutableList<Any>
}