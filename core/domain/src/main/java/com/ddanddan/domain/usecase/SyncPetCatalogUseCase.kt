package com.ddanddan.domain.usecase

import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.entity.PetCatalog
import javax.inject.Inject

class SyncPetCatalogUseCase @Inject constructor(
    private val getLocalCatalogUseCase: GetLocalCatalogUseCase,
    private val fetchPetCatalogUseCase: FetchPetCatalogUseCase,
    private val dataStore: ddanddanDataStore
) {
    suspend operator fun invoke(): PetCatalog {
        if (dataStore.isPetCatalogUpdateRequired) {
            runCatching { fetchPetCatalogUseCase() }.onSuccess {
                dataStore.isPetCatalogUpdateRequired = false
            }
        }
        return getLocalCatalogUseCase()
    }
}
