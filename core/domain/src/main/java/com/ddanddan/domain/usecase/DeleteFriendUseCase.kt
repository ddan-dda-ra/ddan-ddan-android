package com.ddanddan.domain.usecase

import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(fId: String): Result<Unit> {
        return runCatching {
            friendRepository.deleteFriend(fId)
        }
    }
}