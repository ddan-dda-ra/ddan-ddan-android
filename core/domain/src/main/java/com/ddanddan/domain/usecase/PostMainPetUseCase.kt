package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class PostMainPetUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(petId: String) = runCatching {
        userRepository.postMainPet(petId)
    }
}