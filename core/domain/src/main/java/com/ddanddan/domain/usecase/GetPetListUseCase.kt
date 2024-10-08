package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.repository.HomeRepository
import javax.inject.Inject

class GetPetListUseCase @Inject constructor(
    private val petRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Pet>> {
        return runCatching {
            petRepository.getPetList()
        }
    }
}