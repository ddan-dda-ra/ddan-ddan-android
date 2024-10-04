package com.ddanddan.domain.repository

interface UserRepository {
    suspend fun login(token: String): Result<Boolean>
}