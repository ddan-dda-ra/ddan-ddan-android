package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.AuthRepository
import javax.inject.Inject

class DisableAutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
)  {
    operator fun invoke() =
        authRepository.disableAutoLogin()
}