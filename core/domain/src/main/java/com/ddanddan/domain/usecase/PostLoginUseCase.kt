package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.AuthRepository
import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: String) = runCatching {
        authRepository.setFirstAfterInstall(false)
        authRepository.enableAutoLogin()
        userRepository.postLogin(token)
    }
}