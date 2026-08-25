package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R

fun String?.toAnimal(level: Int?): Int {
    return when (this) {
        "CAT" -> {
            when (level) {
                1 -> R.drawable.ic_cat_level1
                2 -> R.drawable.ic_cat_level2
                3 -> R.drawable.ic_cat_level3
                4 -> R.drawable.ic_cat_level4
                else -> R.drawable.ic_cat_level5
            }
        }

        "DOG" -> {
            when (level) {
                1 -> R.drawable.ic_dog_level1
                2 -> R.drawable.ic_dog_level2
                3 -> R.drawable.ic_dog_level3
                4 -> R.drawable.ic_dog_level4
                else -> R.drawable.ic_dog_level5
            }
        }

        "PENGUIN" -> {
            when (level) {
                1 -> R.drawable.ic_penguin_level1
                2 -> R.drawable.ic_penguin_level2
                3 -> R.drawable.ic_penguin_level3
                4 -> R.drawable.ic_penguin_level4
                else -> R.drawable.ic_penguin_level5
            }
        }

        "HAMSTER" -> {
            when (level) {
                1 -> R.drawable.ic_hamster_level1
                2 -> R.drawable.ic_hamster_level2
                3 -> R.drawable.ic_hamster_level3
                4 -> R.drawable.ic_hamster_level4
                else -> R.drawable.ic_hamster_level5
            }
        }

        "MOLE" -> {
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

fun String?.toLottie(level: Int?, isPlayAndEatLottie: Boolean): Int {
    return when (this) {
        "CAT" -> {
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

        "DOG" -> {
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

        "PENGUIN" -> {
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

        "HAMSTER" -> {
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

        "MOLE" -> {
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

        else -> R.raw.motion_cat_level1_default
    }
}

fun String?.toBackgroundImage(): Int {
    return when (this) {
        "CAT" -> R.drawable.ic_bg_cat
        "DOG" -> R.drawable.ic_bg_dog
        "PENGUIN" -> R.drawable.ic_bg_penguin
        "HAMSTER" -> R.drawable.ic_bg_hamster
        "MOLE" -> R.drawable.ic_bg_mole
        else -> R.drawable.ic_bg_transparent
    }
}

fun String?.toRectBackgroundImage(): Int {
    return when (this) {
        "CAT" -> R.drawable.ic_bg_cat_rect
        "DOG" -> R.drawable.ic_bg_dog_rect
        "PENGUIN" -> R.drawable.ic_bg_penguin_rect
        "HAMSTER" -> R.drawable.ic_bg_hamster_rect
        "MOLE" -> R.drawable.ic_bg_mole_rect
        else -> R.drawable.ic_bg_cat_rect
    }
}

fun String?.toColor(): Color {
    return when (this) {
        "CAT" -> Color(0xFFFD85FF)
        "DOG" -> Color(0xFF9B6CFF)
        "PENGUIN" -> Color(0xFF4E95FF)
        "HAMSTER" -> Color(0xFF46F8A2)
        "MOLE" -> Color(0xFFD0DAE4)
        else -> Color(0xFFFD85FF)
    }
}

// 펫 카탈로그의 colorCode("#RRGGBB")를 파싱한다. 알 수 없는 포맷이면 null을 반환해 호출부가 로컬 기본값으로 폴백하게 한다.
fun String?.toColorFromHex(): Color? =
    this?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrNull() }
