package com.ddanddan.model.response

import com.ddanddan.domain.entity.PetCatalog
import com.ddanddan.domain.entity.PetCatalogItem
import com.ddanddan.domain.entity.PetCatalogLevel

data class ResponsePetCatalog(
    val revision: String,
    val pets: List<ResponsePetCatalogItem>
)

data class ResponsePetCatalogItem(
    val type: String,
    val name: String,
    val colorCode: String,
    val displayOrder: Int,
    val levels: List<ResponsePetCatalogLevel>
)

data class ResponsePetCatalogLevel(
    val level: Int,
    val imageUrl: String,
    val lottieDefaultUrl: String,
    val lottiePlayEatUrl: String
)

fun ResponsePetCatalog.toPetCatalog() = PetCatalog(
    revision = revision,
    pets = pets.map { it.toPetCatalogItem() }
)

fun ResponsePetCatalogItem.toPetCatalogItem() = PetCatalogItem(
    type = type,
    name = name,
    colorCode = colorCode,
    displayOrder = displayOrder,
    levels = levels.map { it.toPetCatalogLevel() }
)

fun ResponsePetCatalogLevel.toPetCatalogLevel() = PetCatalogLevel(
    level = level,
    imageUrl = imageUrl,
    lottieDefaultUrl = lottieDefaultUrl,
    lottiePlayEatUrl = lottiePlayEatUrl
)