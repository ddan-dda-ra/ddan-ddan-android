package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.HomeService
import javax.inject.Inject

class RemoteHomeDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun postPlayPet(petId: String) = homeService.postPlayPet(petId)

    suspend fun postFoodPet(petId: String) = homeService.postFoodPet(petId)

    suspend fun getPetList() = homeService.getPetList()
}