package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteUserDataSource
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.entity.User
import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: RemoteUserDataSource
) : UserRepository {
    override suspend fun getUser(): User {
        return userDataSource.getUser().toUser()
    }

    override suspend fun getMainPet(): Pet {
        return userDataSource.getMainPet().mainPet.toPet()
    }
}