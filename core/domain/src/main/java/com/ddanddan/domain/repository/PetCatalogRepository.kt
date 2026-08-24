package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.PetCatalog

interface PetCatalogRepository {
    suspend fun getPetCatalog(): PetCatalog
    suspend fun getCachedRevision(): String?
    suspend fun saveCatalog(catalog: PetCatalog)
    suspend fun getLocalCatalog(): PetCatalog
}