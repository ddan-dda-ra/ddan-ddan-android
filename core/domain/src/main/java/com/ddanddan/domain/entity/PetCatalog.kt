package com.ddanddan.domain.entity

data class PetCatalog(
    val revision: String,
    val pets: List<PetCatalogItem>
)

data class PetCatalogItem(
    val type: String,
    val name: String,
    val colorCode: String,
    val displayOrder: Int,
    val levels: List<PetCatalogLevel>
)

data class PetCatalogLevel(
    val level: Int,
    val imageUrl: String,
    val lottieDefaultUrl: String,
    val lottiePlayEatUrl: String,
    // 로컬에 저장된 파일 경로
    val localImagePath: String? = null,
    val localLottieDefaultPath: String? = null,
    val localLottiePlayEatPath: String? = null
)

fun PetCatalog.findImageUrl(type: String, level: Int): String? =
    pets.find { it.type == type }
        ?.levels?.find { it.level == level }
        ?.imageUrl

fun PetCatalog.findLottieUrl(type: String, level: Int, isPlayEat: Boolean): String? {
    val petLevel = pets.find { it.type == type }?.levels?.find { it.level == level }
    return if (isPlayEat) petLevel?.lottiePlayEatUrl else petLevel?.lottieDefaultUrl
}

fun PetCatalog.findColorHex(type: String): String? =
    pets.find { it.type == type }?.colorCode