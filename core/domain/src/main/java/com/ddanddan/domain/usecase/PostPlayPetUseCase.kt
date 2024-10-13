package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.HomeRepository
import javax.inject.Inject

class PostPlayPetUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(petId: String) = runCatching {
        homeRepository.postPlayPet(petId)
    }
}