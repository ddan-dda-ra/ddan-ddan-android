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