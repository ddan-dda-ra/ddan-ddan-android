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

fun PetTypeEnum?.toColor(): Color {
    return when (this) {
        PetTypeEnum.CAT -> Color(0xFFFD85FF)
        PetTypeEnum.DOG -> Color(0xFF86D32E)
        PetTypeEnum.PENGUIN -> Color(0xFF3F51B5)
        PetTypeEnum.HAMSTER -> Color(0xFF009688)
        else -> Color(0xFF00BCD4)
    }
}