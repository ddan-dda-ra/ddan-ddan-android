package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.PetCatalogRepository
import javax.inject.Inject

class FetchPetCatalogUseCase @Inject constructor(
    private val petCatalogRepository: PetCatalogRepository
) {
    suspend operator fun invoke() = petCatalogRepository.getPetCatalog()
}