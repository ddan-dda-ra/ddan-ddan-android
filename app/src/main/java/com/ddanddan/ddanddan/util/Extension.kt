package com.ddanddan.ddanddan.util

import com.ddanddan.ddanddan.R
import com.ddanddan.domain.enum.PetTypeEnum

fun PetTypeEnum?.toImage(): Int {
    return when (this) {
        PetTypeEnum.CAT -> R.drawable.ic_cat
        PetTypeEnum.DOG -> R.drawable.ic_cat
        PetTypeEnum.PENGUIN -> R.drawable.ic_cat
        PetTypeEnum.HAMSTER -> R.drawable.ic_cat
        else -> R.drawable.ic_question
    }
}