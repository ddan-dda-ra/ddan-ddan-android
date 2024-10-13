package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemotePetDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.UserPet
import com.ddanddan.domain.repository.PetRepository
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val petDataSource: RemotePetDataSource
): PetRepository {
    override suspend fun getPetList(): List<Pet> {
        return petDataSource.getPetList().pets.map { it.toPet() }
    }

    override suspend fun postPlayPet(petId: String): UserPet {
        return petDataSource.postPlayPet(petId).toUserPet()
    }

    override suspend fun postFoodPet(petId: String): UserPet {
        return petDataSource.postFoodPet(petId).toUserPet()
    }
}