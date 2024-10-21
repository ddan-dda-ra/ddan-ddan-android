package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class PutUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(name: String, purposeCalorie: Int) = runCatching {
        userRepository.putUser(name, purposeCalorie)
    }
}