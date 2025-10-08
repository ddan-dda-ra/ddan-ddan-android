package com.ddanddan.domain.entity

import com.ddanddan.domain.enums.PetTypeEnum

data class Friend(
    val id: String,
    val name: String,
    val mainPetType: PetTypeEnum,
    val petLevel: Int
)