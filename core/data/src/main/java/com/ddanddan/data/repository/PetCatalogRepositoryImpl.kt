package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemotePetDataSource
import com.ddanddan.data.local.PetCatalogDao
import com.ddanddan.data.local.toEntityList
import com.ddanddan.data.local.toLevelEntityList
import com.ddanddan.data.local.toPetCatalog
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.entity.PetCatalog
import com.ddanddan.domain.repository.PetCatalogRepository
import com.ddanddan.model.response.toPetCatalog
import javax.inject.Inject

class PetCatalogRepositoryImpl @Inject constructor(
    private val remotePetDataSource: RemotePetDataSource,
    private val petCatalogDao: PetCatalogDao,
    private val dataStore: ddanddanDataStore
) : PetCatalogRepository {

    override suspend fun getCachedRevision(): String? {
        return dataStore.petCatalogRevision // DataStore에 revision 저장
    }

    override suspend fun getPetCatalog(): PetCatalog {
        val cachedRevision = getCachedRevision()
        val response = remotePetDataSource.getPetCatalog(cachedRevision)

        return if (response.code() == 304) {
            // 변경 없으면 로컬에서 반환
            getLocalCatalog()
        } else {
            // 새 데이터 저장 후 반환
            val catalog = response.body()!!.toPetCatalog()
            saveCatalog(catalog)
            catalog
        }
    }

    override suspend fun saveCatalog(catalog: PetCatalog) {
        petCatalogDao.deleteAllPets()
        petCatalogDao.deleteAllLevels()
        petCatalogDao.insertPets(catalog.pets.toEntityList(catalog.revision))
        petCatalogDao.insertLevels(catalog.pets.toLevelEntityList())
    }

    private suspend fun getLocalCatalog(): PetCatalog {
        val pets = petCatalogDao.getAllPets()
        val levels = pets.map { pet ->
            petCatalogDao.getLevelsByPetType(pet.type)
        }
        return pets.toPetCatalog(levels)
    }
}