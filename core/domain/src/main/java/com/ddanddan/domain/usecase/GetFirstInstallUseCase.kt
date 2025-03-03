package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.AuthRepository
import javax.inject.Inject

class GetFirstInstallUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.isFirstAfterInstall()
}