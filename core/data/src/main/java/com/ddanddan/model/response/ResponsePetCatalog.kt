package com.ddanddan.model.response

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