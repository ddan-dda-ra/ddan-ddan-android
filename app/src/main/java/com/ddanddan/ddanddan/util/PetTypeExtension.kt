package com.ddanddan.ddanddan.util

import androidx.compose.ui.graphics.Color
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.entity.PetCatalog
import com.ddanddan.domain.entity.findColorHex
import com.ddanddan.domain.entity.findImageUrl
import com.ddanddan.domain.entity.findLottieUrl

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

// 카탈로그에 해당 타입/레벨이 있으면 그 값을, 없으면(카탈로그 미로딩·새 타입 등) 번들 리소스 기반 기본값을 쓴다.
fun PetCatalog?.colorFor(type: String?): Color =
    type?.let { this?.findColorHex(it)?.toColorFromHex() } ?: type.toColor()

fun PetCatalog?.imageUrlFor(type: String?, level: Int?): String? =
    if (type == null || level == null) null else this?.findImageUrl(type, level)

fun PetCatalog?.lottieUrlFor(type: String?, level: Int?, isPlayEat: Boolean): String? =
    if (type == null || level == null) null else this?.findLottieUrl(type, level, isPlayEat)

// PetBackground처럼 원본 hex 문자열이 필요한 곳을 위한 버전. 카탈로그에 없으면 toColor()와 같은 기본값의 hex 표현을 쓴다.
fun String?.toColorHex(): String = when (this) {
    "CAT" -> "#FD85FF"
    "DOG" -> "#9B6CFF"
    "PENGUIN" -> "#4E95FF"
    "HAMSTER" -> "#46F8A2"
    "MOLE" -> "#D0DAE4"
    else -> "#FD85FF"
}

fun PetCatalog?.colorHexFor(type: String?): String =
    type?.let { this?.findColorHex(it) } ?: type.toColorHex()
