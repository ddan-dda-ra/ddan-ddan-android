package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class GetInviteCodeUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): Result<String> {
        return runCatching {
            friendRepository.getInviteCode()
        }
    }
}