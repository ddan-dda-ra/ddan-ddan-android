package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.PetCatalog
import com.ddanddan.domain.entity.UserPet

interface PetRepository {
    suspend fun getPetList(): List<Pet>
    suspend fun postPlayPet(petId: String): UserPet
    suspend fun postFoodPet(petId: String): UserPet
    suspend fun postTypePet(petType: String): Pet
    suspend fun postRandomPet(): Pet
    suspend fun getPetCatalog(): PetCatalog
    suspend fun savePetCatalog(catalog: PetCatalog)
    fun getCachedCatalogRevision(): String?
}