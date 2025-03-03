package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.AuthRepository
import javax.inject.Inject

class GetAutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() =
        authRepository.getAutoLogin()
}