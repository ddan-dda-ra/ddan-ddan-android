package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteUserDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: RemoteUserDataSource,
    private val ddanddanDataStore: ddanddanDataStore
) : UserRepository {
    override suspend fun getUser(): User {
        return userDataSource.getUser().toUser()
    }

    override suspend fun putUser(name: String, purposeCalorie: Int): User {
        return userDataSource.putUser(name, purposeCalorie).toUser()
    }

    override suspend fun getMainPet(): Pet {
        return userDataSource.getMainPet().mainPet.toPet()
    }

    override suspend fun postMainPet(petId: String): Pet {
        return userDataSource.postMainPet(petId).mainPet.toPet()
    }

    override suspend fun login(token: String): Result<Boolean> {
        return runCatching {
            val result = userDataSource.login(token)

            ddanddanDataStore.userToken = "Bearer ${result.accessToken}"
            ddanddanDataStore.refreshToken = "Bearer ${result.refreshToken}"

            result.isOnboardingComplete
        }
    }
}