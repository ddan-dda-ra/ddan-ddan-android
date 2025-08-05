package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R

fun PetTypeEnum.toAnimal(level: Int?): Int {
    return when (this) {
        PetTypeEnum.CAT -> {
            when (level) {
                2 -> com.ddanddan.ddanddan.R.drawable.ic_cat_level2
                3 -> com.ddanddan.ddanddan.R.drawable.ic_cat_level3
                4 -> com.ddanddan.ddanddan.R.drawable.ic_cat_level4
                5 -> com.ddanddan.ddanddan.R.drawable.ic_cat_level5
                else -> com.ddanddan.ddanddan.R.drawable.ic_cat_level1
            }
        }

        PetTypeEnum.DOG -> {
            when (level) {
                2 -> com.ddanddan.ddanddan.R.drawable.ic_dog_level2
                3 -> com.ddanddan.ddanddan.R.drawable.ic_dog_level3
                4 -> com.ddanddan.ddanddan.R.drawable.ic_dog_level4
                5 -> com.ddanddan.ddanddan.R.drawable.ic_dog_level5
                else -> com.ddanddan.ddanddan.R.drawable.ic_dog_level1
            }
        }

        PetTypeEnum.PENGUIN -> {
            when (level) {
                2 -> com.ddanddan.ddanddan.R.drawable.ic_penguin_level2
                3 -> com.ddanddan.ddanddan.R.drawable.ic_penguin_level3
                4 -> com.ddanddan.ddanddan.R.drawable.ic_penguin_level4
                5 -> com.ddanddan.ddanddan.R.drawable.ic_penguin_level5
                else -> com.ddanddan.ddanddan.R.drawable.ic_penguin_level1
            }
        }

        PetTypeEnum.HAMSTER -> {
            when (level) {
                2 -> com.ddanddan.ddanddan.R.drawable.ic_hamster_level2
                3 -> com.ddanddan.ddanddan.R.drawable.ic_hamster_level3
                4 -> com.ddanddan.ddanddan.R.drawable.ic_hamster_level4
                5 -> com.ddanddan.ddanddan.R.drawable.ic_hamster_level5
                else -> com.ddanddan.ddanddan.R.drawable.ic_hamster_level1

            }
        }

        PetTypeEnum.MOLE -> {
            when (level) {
                2 -> R.drawable.ic_mole_level2
                3 -> R.drawable.ic_mole_level3
                4 -> R.drawable.ic_mole_level4
                5 -> R.drawable.ic_mole_level5
                else -> R.drawable.ic_mole_level1
            }
        }

        else -> com.ddanddan.ddanddan.R.drawable.ic_question
    }
}

fun PetTypeEnum?.toBackgroundImage(): Int {
    return when (this) {
        PetTypeEnum.CAT -> com.ddanddan.ddanddan.R.drawable.ic_bg_cat
        PetTypeEnum.DOG -> com.ddanddan.ddanddan.R.drawable.ic_bg_dog
        PetTypeEnum.PENGUIN -> com.ddanddan.ddanddan.R.drawable.ic_bg_penguin
        PetTypeEnum.HAMSTER -> com.ddanddan.ddanddan.R.drawable.ic_bg_hamster
        PetTypeEnum.MOLE -> com.ddanddan.ddanddan.R.drawable.ic_bg_mole
        else -> com.ddanddan.ddanddan.R.drawable.ic_bg_cat
    }
}

fun PetTypeEnum?.toColor(): Color {
    return when (this) {
        PetTypeEnum.CAT -> Color(0xFFFD85FF)
        PetTypeEnum.DOG -> Color(0xFF9B6CFF)
        PetTypeEnum.PENGUIN -> Color(0xFF4E95FF)
        PetTypeEnum.HAMSTER -> Color(0xFF46F8A2)
        PetTypeEnum.MOLE -> Color(0xFFD0DAE4)
        else -> Color(0xFFFD85FF)
    }
}

enum class PetTypeEnum {
    CAT, HAMSTER, PENGUIN, DOG, MOLE
}

fun String.toPetType(): PetTypeEnum {
    return when (this) {
        "CAT" -> PetTypeEnum.CAT
        "HAMSTER" -> PetTypeEnum.HAMSTER
        "PENGUIN" -> PetTypeEnum.PENGUIN
        "DOG" -> PetTypeEnum.DOG
        "MOLE" -> PetTypeEnum.MOLE
        else -> PetTypeEnum.CAT
    }
}