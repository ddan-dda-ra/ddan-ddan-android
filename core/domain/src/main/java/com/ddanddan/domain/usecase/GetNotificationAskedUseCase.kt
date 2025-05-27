package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.AuthRepository
import javax.inject.Inject

class GetNotificationAskedUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(): Boolean {
        val isAsked = authRepository.isNotificationAsked()
        if (!isAsked) authRepository.setNotificationAsked(true)
        return isAsked
    }
}