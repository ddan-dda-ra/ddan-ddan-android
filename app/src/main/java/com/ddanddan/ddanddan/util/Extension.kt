package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.enums.PetTypeEnum

fun PetTypeEnum?.toImage(): Int {
    return when (this) {
        PetTypeEnum.CAT -> R.drawable.ic_cat
        PetTypeEnum.DOG -> R.drawable.ic_cat
        PetTypeEnum.PENGUIN -> R.drawable.ic_cat
        PetTypeEnum.HAMSTER -> R.drawable.ic_cat
        else -> R.drawable.ic_question
    }
}

fun PetTypeEnum?.toLottie(): Int {
    return when (this) {
        PetTypeEnum.CAT -> R.raw.motion_cat_level4_default
        PetTypeEnum.DOG -> R.raw.motion_cat_level4_default
        PetTypeEnum.PENGUIN -> R.raw.motion_cat_level4_default
        PetTypeEnum.HAMSTER -> R.raw.motion_cat_level4_default
        else -> R.raw.motion_cat_level4_default
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