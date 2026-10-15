package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.PetCatalogRepository
import javax.inject.Inject

class GetLocalCatalogUseCase @Inject constructor(
    private val petCatalogRepository: PetCatalogRepository
) {
    suspend operator fun invoke() = petCatalogRepository.getLocalCatalog()
}