package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val disableAutoLoginUseCase: DisableAutoLoginUseCase
) {
    suspend operator fun invoke() = runCatching {
        userRepository.deleteUser()
    }.fold(
        onSuccess = {
            if (it) disableAutoLoginUseCase()
            it
        }, onFailure = {
            false
        })
}