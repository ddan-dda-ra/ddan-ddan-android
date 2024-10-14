package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = runCatching {
        userRepository.getUser()
    }
}