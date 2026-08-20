package com.ddanddan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_catalog")
data class PetCatalogEntity(
    @PrimaryKey
    val type: String,
    val name: String,
    val colorCode: String,
    val displayOrder: Int,
    val revision: String
)

@Entity(tableName = "pet_catalog_level")
data class PetCatalogLevelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val petType: String,
    val level: Int,
    val imageUrl: String,
    val lottieDefaultUrl: String,
    val lottiePlayEatUrl: String,
    val localImagePath: String? = null,
    val localLottieDefaultPath: String? = null,
    val localLottiePlayEatPath: String? = null
)