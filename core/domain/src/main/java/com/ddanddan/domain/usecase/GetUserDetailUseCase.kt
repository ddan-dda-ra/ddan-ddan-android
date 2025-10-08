package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.UserDetail
import com.ddanddan.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uId: String): Result<UserDetail> {
        return runCatching {
            userRepository.getUserDetail(uId)
        }
    }
}