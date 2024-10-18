package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.UserPet
import com.ddanddan.domain.enum.PetTypeEnum

interface PetRepository {
    suspend fun getPetList(): List<Pet>
    suspend fun postPlayPet(petId: String): UserPet
    suspend fun postFoodPet(petId: String): UserPet
    suspend fun postTypePet(petTypeEnum: PetTypeEnum): Pet
}