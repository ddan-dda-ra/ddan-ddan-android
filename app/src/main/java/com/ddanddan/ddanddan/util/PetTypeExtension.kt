package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.enums.PetTypeEnum

fun PetTypeEnum?.toAnimal(level: Int?): Int {
    return when (this) {
        PetTypeEnum.CAT -> {
            when(level) {
                2 -> R.drawable.ic_cat_level2
                3 -> R.drawable.ic_cat_level3
                4 -> R.drawable.ic_cat_level4
                5 -> R.drawable.ic_cat_level5
                else -> R.drawable.ic_cat_level1
            }
        }
        PetTypeEnum.DOG -> {
            when(level) {
                2 -> R.drawable.ic_dog_level2
                3 -> R.drawable.ic_dog_level3
                4 -> R.drawable.ic_dog_level4
                5 -> R.drawable.ic_dog_level5
                else -> R.drawable.ic_dog_level1
            }
        }
        PetTypeEnum.PENGUIN -> {
            when(level) {
                2 -> R.drawable.ic_penguin_level2
                3 -> R.drawable.ic_penguin_level3
                4 -> R.drawable.ic_penguin_level4
                5 -> R.drawable.ic_penguin_level5
                else -> R.drawable.ic_penguin_level1
            }
        }
        PetTypeEnum.HAMSTER -> {
            when(level) {
                2 -> R.drawable.ic_hamster_level2
                3 -> R.drawable.ic_hamster_level3
                4 -> R.drawable.ic_hamster_level4
                5 -> R.drawable.ic_hamster_level5
                else -> R.drawable.ic_hamster_level1

            }
        }
        else -> R.drawable.ic_question
    }
}

fun PetTypeEnum?.toLottie(level: Int?): Int {
    return when (this) {
        PetTypeEnum.CAT -> {
            when(level) {
                2 -> R.raw.motion_cat_level5_default
                3 -> R.raw.motion_cat_level5_default
                4 -> R.raw.motion_cat_level5_default
                5 -> R.raw.motion_cat_level5_default
                else -> R.raw.motion_cat_level4_default
            }
        }
        PetTypeEnum.DOG -> {
            when(level) {
                2 -> R.raw.motion_cat_level5_default
                3 -> R.raw.motion_cat_level5_default
                4 -> R.raw.motion_cat_level5_default
                5 -> R.raw.motion_cat_level5_default
                else -> R.raw.motion_cat_level4_default
            }
        }
        PetTypeEnum.PENGUIN -> {
            when(level) {
                2 -> R.raw.motion_cat_level5_default
                3 -> R.raw.motion_cat_level5_default
                4 -> R.raw.motion_cat_level5_default
                5 -> R.raw.motion_cat_level5_default
                else -> R.raw.motion_cat_level4_default
            }
        }
        PetTypeEnum.HAMSTER -> {
            when(level) {
                2 -> R.raw.motion_cat_level5_default
                3 -> R.raw.motion_cat_level5_default
                4 -> R.raw.motion_cat_level5_default
                5 -> R.raw.motion_cat_level5_default
                else -> R.raw.motion_cat_level4_default
            }
        }
        else -> R.raw.motion_cat_level5_default
    }
}

fun PetTypeEnum?.toBackgroundImage(): Int {
    return when (this) {
        PetTypeEnum.CAT -> R.drawable.ic_bg_cat
        PetTypeEnum.DOG -> R.drawable.ic_bg_dog
        PetTypeEnum.PENGUIN -> R.drawable.ic_bg_penguin
        PetTypeEnum.HAMSTER -> R.drawable.ic_bg_hamster
        else -> R.drawable.ic_bg_cat
    }
}

fun PetTypeEnum?.toColor(): Color {
    return when (this) {
        PetTypeEnum.CAT -> Color(0xFFFD85FF)
        PetTypeEnum.DOG -> Color(0xFF9B6CFF)
        PetTypeEnum.PENGUIN -> Color(0xFF4E95FF)
        PetTypeEnum.HAMSTER -> Color(0xFF46F8A2)
        else -> Color(0xFFFD85FF)
    }
}