package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.enums.PetTypeEnum

fun PetTypeEnum?.toAnimal(level: Int?): Int {
    return when (this) {
        PetTypeEnum.CAT -> {
            when (level) {
                1 -> R.drawable.ic_cat_level1
                2 -> R.drawable.ic_cat_level2
                3 -> R.drawable.ic_cat_level3
                4 -> R.drawable.ic_cat_level4
                else -> R.drawable.ic_cat_level5
            }
        }

        PetTypeEnum.DOG -> {
            when (level) {
                1 -> R.drawable.ic_dog_level1
                2 -> R.drawable.ic_dog_level2
                3 -> R.drawable.ic_dog_level3
                4 -> R.drawable.ic_dog_level4
                else -> R.drawable.ic_dog_level5
            }
        }

        PetTypeEnum.PENGUIN -> {
            when (level) {
                1 -> R.drawable.ic_penguin_level1
                2 -> R.drawable.ic_penguin_level2
                3 -> R.drawable.ic_penguin_level3
                4 -> R.drawable.ic_penguin_level4
                else -> R.drawable.ic_penguin_level5
            }
        }

        PetTypeEnum.HAMSTER -> {
            when (level) {
                1 -> R.drawable.ic_hamster_level1
                2 -> R.drawable.ic_hamster_level2
                3 -> R.drawable.ic_hamster_level3
                4 -> R.drawable.ic_hamster_level4
                else -> R.drawable.ic_hamster_level5
            }
        }

        PetTypeEnum.MOLE -> {
            when (level) {
                1 -> R.drawable.ic_mole_level1
                2 -> R.drawable.ic_mole_level2
                3 -> R.drawable.ic_mole_level3
                4 -> R.drawable.ic_mole_level4
                else -> R.drawable.ic_mole_level5
            }
        }

        else -> R.drawable.ic_question
    }
}

fun PetTypeEnum?.toLottie(level: Int?, isPlayAndEatLottie: Boolean): Int {
    return when (this) {
        PetTypeEnum.CAT -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    1 -> R.raw.motion_cat_level1_default
                    2 -> R.raw.motion_cat_level2_default
                    3 -> R.raw.motion_cat_level3_default
                    4 -> R.raw.motion_cat_level4_default
                    else -> R.raw.motion_cat_level5_default
                }

                else -> when (level) {
                    1 -> R.raw.motion_cat_level1_play_eat
                    2 -> R.raw.motion_cat_level2_play_eat
                    3 -> R.raw.motion_cat_level3_play_eat
                    4 -> R.raw.motion_cat_level4_play_eat
                    else -> R.raw.motion_cat_level5_play_eat
                }
            }
        }

        PetTypeEnum.DOG -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    1 -> R.raw.motion_puppy_level1_default
                    2 -> R.raw.motion_puppy_level2_default
                    3 -> R.raw.motion_puppy_level3_default
                    4 -> R.raw.motion_puppy_level4_default
                    else -> R.raw.motion_puppy_level5_default
                }

                else -> when (level) {
                    1 -> R.raw.motion_puppy_level1_play_eat
                    2 -> R.raw.motion_puppy_level2_play_eat
                    3 -> R.raw.motion_puppy_level3_play_eat
                    4 -> R.raw.motion_puppy_level4_play_eat
                    else -> R.raw.motion_puppy_level5_play_eat
                }
            }
        }

        PetTypeEnum.PENGUIN -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    1 -> R.raw.motion_penguin_level1_default
                    2 -> R.raw.motion_penguin_level2_default
                    3 -> R.raw.motion_penguin_level3_default
                    4 -> R.raw.motion_penguin_level4_default
                    else -> R.raw.motion_penguin_level5_default
                }

                else -> when (level) {
                    1 -> R.raw.motion_penguin_level1_play_eat
                    2 -> R.raw.motion_penguin_level2_play_eat
                    3 -> R.raw.motion_penguin_level3_play_eat
                    4 -> R.raw.motion_penguin_level4_play_eat
                    else -> R.raw.motion_penguin_level5_play_eat
                }
            }
        }

        PetTypeEnum.HAMSTER -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    1 -> R.raw.motion_hamster_level1_default
                    2 -> R.raw.motion_hamster_level2_default
                    3 -> R.raw.motion_hamster_level3_default
                    4 -> R.raw.motion_hamster_level4_default
                    else -> R.raw.motion_hamster_level5_default
                }

                else -> when (level) {
                    1 -> R.raw.motion_hamster_level1_play_eat
                    2 -> R.raw.motion_hamster_level2_play_eat
                    3 -> R.raw.motion_hamster_level3_play_eat
                    4 -> R.raw.motion_hamster_level4_play_eat
                    else -> R.raw.motion_hamster_level5_play_eat
                }
            }
        }

        PetTypeEnum.MOLE -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    1 -> R.raw.motion_mole_level1_default
                    2 -> R.raw.motion_mole_level2_default
                    3 -> R.raw.motion_mole_level3_default
                    4 -> R.raw.motion_mole_level4_default
                    else -> R.raw.motion_mole_level5_default
                }

                else -> when (level) {
                    1 -> R.raw.motion_mole_level1_play_eat
                    2 -> R.raw.motion_mole_level2_play_eat
                    3 -> R.raw.motion_mole_level3_play_eat
                    4 -> R.raw.motion_mole_level4_play_eat
                    else -> R.raw.motion_mole_level5_play_eat
                }
            }
        }

        PetTypeEnum.MOLE -> {
            when (isPlayAndEatLottie) {
                false -> when (level) {
                    2 -> R.raw.motion_mole_level2_default
                    3 -> R.raw.motion_mole_level3_default
                    4 -> R.raw.motion_mole_level4_default
                    5 -> R.raw.motion_mole_level5_dafault
                    else -> R.raw.motion_mole_level1_default
                }

                else -> when (level) {
                    2 -> R.raw.motion_mole_level2_play_eat
                    3 -> R.raw.motion_mole_level3_play_eat
                    4 -> R.raw.motion_mole_level4_play_eat
                    5 -> R.raw.motion_mole_level5_play_eat
                    else -> R.raw.motion_mole_level1_play_eat
                }
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
        PetTypeEnum.MOLE -> R.drawable.ic_bg_mole
        else -> R.drawable.ic_bg_cat
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