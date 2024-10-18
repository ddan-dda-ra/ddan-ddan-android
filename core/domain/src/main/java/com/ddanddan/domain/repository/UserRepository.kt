package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User

interface UserRepository {
    suspend fun getUser(): User
    suspend fun getMainPet(): Pet
    suspend fun postMainPet(petId: String): Pet
    suspend fun login(token: String): Result<Boolean>
}