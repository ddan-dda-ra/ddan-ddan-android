package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.repository.PetRepository
import javax.inject.Inject

class GetPetListUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(): Result<List<Pet>> {
        return runCatching {
            petRepository.getPetList()
        }
    }
}