package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.PetService
import com.ddanddan.model.request.RequestTypePet
import javax.inject.Inject

class RemotePetDataSource @Inject constructor(
    private val petService: PetService
) {
    suspend fun postPlayPet(petId: String) = petService.postPlayPet(petId)

    suspend fun postFoodPet(petId: String) = petService.postFoodPet(petId)

    suspend fun getPetList() = petService.getPetList()

    suspend fun postTypePet(petType: String) = petService.postTypePet(RequestTypePet(petType))
}