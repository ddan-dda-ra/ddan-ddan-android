package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.PetRepository
import javax.inject.Inject

class PostPlayPetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(petId: String) = runCatching {
        petRepository.postPlayPet(petId)
    }
}