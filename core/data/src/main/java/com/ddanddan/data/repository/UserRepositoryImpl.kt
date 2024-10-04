package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteUserDataSource
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: RemoteUserDataSource,
    private val ddanddanDataStore: ddanddanDataStore
): UserRepository {
    override suspend fun login(token: String): Result<Boolean> {
        return runCatching {
            val result = userDataSource.login(token)

            ddanddanDataStore.userToken = result.accessToken
            ddanddanDataStore.refreshToken = result.refreshToken

            result.isOnboardingComplete
        }
    }
}