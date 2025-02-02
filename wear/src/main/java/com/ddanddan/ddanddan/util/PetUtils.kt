package com.ddanddan.ddanddan.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.domain.entity.MainPet

object PetUtils {

    fun getDrawableResourceId(mainPet: MainPet): Int {
        val petType = PetType.fromType(mainPet.type) ?: return R.drawable.ic_cat_level1 //todo - null 시 대체할 drawable은 논의 필요

        /**
         * 런타임 오류 가능성 및 성능 문제로 getIdentifier를 사용하지 않음
         */
        return when (petType) {
            PetType.DOG -> when (mainPet.level) {
                1 -> R.drawable.ic_dog_level1
                2 -> R.drawable.ic_dog_level2
                3 -> R.drawable.ic_dog_level3
                4 -> R.drawable.ic_dog_level4
                else -> R.drawable.ic_dog_level5 //todo - else에 해당되는 drawable은 논의 필요
            }
            PetType.PENGUIN -> when (mainPet.level) {
                1 -> R.drawable.ic_penguin_level1
                2 -> R.drawable.ic_penguin_level2
                3 -> R.drawable.ic_penguin_level3
                4 -> R.drawable.ic_penguin_level4
                else -> R.drawable.ic_penguin_level5
            }
            PetType.CAT -> when (mainPet.level) {
                1 -> R.drawable.ic_cat_level1
                2 -> R.drawable.ic_cat_level2
                3 -> R.drawable.ic_cat_level3
                4 -> R.drawable.ic_cat_level4
                else -> R.drawable.ic_cat_level5
            }
            PetType.HAMSTER -> when (mainPet.level) {
                1 -> R.drawable.ic_hamster_level1
                2 -> R.drawable.ic_hamster_level2
                3 -> R.drawable.ic_hamster_level3
                4 -> R.drawable.ic_hamster_level4
                else -> R.drawable.ic_hamster_level5
            }
        }
    }

    @Composable
    fun getProgressBarColor(mainPet: MainPet): Color {
        val petType = PetType.fromType(mainPet.type) ?: return Color(0xFFFD85FF)

        return when (petType) {
            PetType.DOG -> Color(0xFF9B6CFF)
            PetType.PENGUIN -> Color(0xFF4E95FF)
            PetType.CAT -> Color(0xFFFD85FF)
            PetType.HAMSTER -> Color(0xFF46F8A2)
        }
    }
}
