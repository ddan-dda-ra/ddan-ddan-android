package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.Play

interface HomeRepository {
    suspend fun getPetList(): List<Pet>
    suspend fun postPlayPet(petId: String): Play
    suspend fun postFoodPet(petId: String): Play
}