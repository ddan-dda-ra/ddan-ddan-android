package com.ddanddan.domain.entity

import com.ddanddan.domain.enums.PetTypeEnum

data class Pet(
    val id: String,
    val type: PetTypeEnum,
    val level: Int,
    val expPercent: Double
)