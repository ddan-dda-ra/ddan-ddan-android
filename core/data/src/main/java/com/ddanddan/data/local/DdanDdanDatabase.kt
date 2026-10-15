package com.ddanddan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PetCatalogEntity::class,
        PetCatalogLevelEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DdanDdanDatabase : RoomDatabase() {
    abstract fun petCatalogDao(): PetCatalogDao
}