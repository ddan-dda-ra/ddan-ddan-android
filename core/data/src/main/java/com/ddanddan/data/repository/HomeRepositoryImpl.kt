package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteHomeDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.UserPet
import com.ddanddan.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: RemoteHomeDataSource
): HomeRepository {
    override suspend fun getPetList(): List<Pet> {
        return homeDataSource.getPetList().pets.map { it.toPet() }
    }

    override suspend fun postPlayPet(petId: String): UserPet {
        return homeDataSource.postPlayPet(petId).toUserPet()
    }

    override suspend fun postFoodPet(petId: String): UserPet {
        return homeDataSource.postFoodPet(petId).toUserPet()
    }
}