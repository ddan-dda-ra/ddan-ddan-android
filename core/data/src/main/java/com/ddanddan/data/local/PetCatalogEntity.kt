package com.ddanddan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ddanddan.domain.entity.PetCatalog
import com.ddanddan.domain.entity.PetCatalogItem
import com.ddanddan.domain.entity.PetCatalogLevel

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

fun List<PetCatalogEntity>.toPetCatalog(levels: List<List<PetCatalogLevelEntity>>): PetCatalog {
    return PetCatalog(
        revision = firstOrNull()?.revision ?: "",
        pets = mapIndexed { index, pet ->
            PetCatalogItem(
                type = pet.type,
                name = pet.name,
                colorCode = pet.colorCode,
                displayOrder = pet.displayOrder,
                levels = levels[index].map { it.toPetCatalogLevel() }
            )
        }
    )
}

fun PetCatalogLevelEntity.toPetCatalogLevel(): PetCatalogLevel {
    return PetCatalogLevel(
        level = level,
        imageUrl = imageUrl,
        lottieDefaultUrl = lottieDefaultUrl,
        lottiePlayEatUrl = lottiePlayEatUrl,
        localImagePath = localImagePath,
        localLottieDefaultPath = localLottieDefaultPath,
        localLottiePlayEatPath = localLottiePlayEatPath
    )
}

fun List<PetCatalogItem>.toEntityList(revision: String): List<PetCatalogEntity> {
    return map { pet ->
        PetCatalogEntity(
            type = pet.type,
            name = pet.name,
            colorCode = pet.colorCode,
            displayOrder = pet.displayOrder,
            revision = revision
        )
    }
}

fun List<PetCatalogItem>.toLevelEntityList(): List<PetCatalogLevelEntity> {
    return flatMap { pet ->
        pet.levels.map { level ->
            PetCatalogLevelEntity(
                petType = pet.type,
                level = level.level,
                imageUrl = level.imageUrl,
                lottieDefaultUrl = level.lottieDefaultUrl,
                lottiePlayEatUrl = level.lottiePlayEatUrl
            )
        }
    }
}