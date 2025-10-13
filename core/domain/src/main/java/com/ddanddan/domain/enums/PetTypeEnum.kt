package com.ddanddan.domain.enums

enum class PetTypeEnum {
    CAT, HAMSTER, PENGUIN, DOG, MOLE
}

fun PetTypeEnum.toString() = when(this) {
    PetTypeEnum.CAT -> "CAT"
    PetTypeEnum.HAMSTER -> "HAMSTER"
    PetTypeEnum.PENGUIN -> "PENGUIN"
    PetTypeEnum.DOG -> "DOG"
    PetTypeEnum.MOLE -> "MOLE"
}

fun String.toPetTypeEnum() = when(this) {
    "CAT" -> PetTypeEnum.CAT
    "HAMSTER" -> PetTypeEnum.HAMSTER
    "PENGUIN" -> PetTypeEnum.PENGUIN
    "DOG" -> PetTypeEnum.DOG
    "MOLE" -> PetTypeEnum.MOLE
    else -> PetTypeEnum.CAT
}