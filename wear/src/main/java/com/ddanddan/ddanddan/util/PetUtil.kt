package com.ddanddan.ddanddan.util

import com.ddanddan.ddanddan.R

/**
 * 워치에 번들된 펫 이미지. 카탈로그 URL을 못 받았을 때(구버전 폰, 오프라인 등)의 대체용이라
 * 번들에 없는 펫 타입은 물음표 이미지를 돌려준다.
 */
fun String?.toAnimal(level: Int?): Int {
    return when (this) {
        "CAT" -> when (level) {
            2 -> R.drawable.ic_cat_level2
            3 -> R.drawable.ic_cat_level3
            4 -> R.drawable.ic_cat_level4
            5 -> R.drawable.ic_cat_level5
            else -> R.drawable.ic_cat_level1
        }

        "DOG" -> when (level) {
            2 -> R.drawable.ic_dog_level2
            3 -> R.drawable.ic_dog_level3
            4 -> R.drawable.ic_dog_level4
            5 -> R.drawable.ic_dog_level5
            else -> R.drawable.ic_dog_level1
        }

        "PENGUIN" -> when (level) {
            2 -> R.drawable.ic_penguin_level2
            3 -> R.drawable.ic_penguin_level3
            4 -> R.drawable.ic_penguin_level4
            5 -> R.drawable.ic_penguin_level5
            else -> R.drawable.ic_penguin_level1
        }

        "HAMSTER" -> when (level) {
            2 -> R.drawable.ic_hamster_level2
            3 -> R.drawable.ic_hamster_level3
            4 -> R.drawable.ic_hamster_level4
            5 -> R.drawable.ic_hamster_level5
            else -> R.drawable.ic_hamster_level1
        }

        "MOLE" -> when (level) {
            2 -> R.drawable.ic_mole_level2
            3 -> R.drawable.ic_mole_level3
            4 -> R.drawable.ic_mole_level4
            5 -> R.drawable.ic_mole_level5
            else -> R.drawable.ic_mole_level1
        }

        else -> R.drawable.ic_question
    }
}
