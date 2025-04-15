package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class PatchDailyCaloriesUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(calorie: Int) = runCatching {
        userRepository.patchDailyCalories(calorie)
    }
}