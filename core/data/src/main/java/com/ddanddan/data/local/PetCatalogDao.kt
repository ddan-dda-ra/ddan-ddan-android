package com.ddanddan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PetCatalogDao {
    @Query("SELECT * FROM pet_catalog ORDER BY displayOrder")
    suspend fun getAllPets(): List<PetCatalogEntity>

    @Query("SELECT * FROM pet_catalog_level WHERE petType = :petType")
    suspend fun getLevelsByPetType(petType: String): List<PetCatalogLevelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(pets: List<PetCatalogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevels(levels: List<PetCatalogLevelEntity>)

    @Query("DELETE FROM pet_catalog")
    suspend fun deleteAllPets()

    @Query("DELETE FROM pet_catalog_level")
    suspend fun deleteAllLevels()
}