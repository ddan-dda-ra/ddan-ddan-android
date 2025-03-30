package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class PatchPushSettingUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(isOn: Boolean) = runCatching {
        userRepository.patchPushSetting(isOn)
    }
}