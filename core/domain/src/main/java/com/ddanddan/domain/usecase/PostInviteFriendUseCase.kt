package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class PostInviteFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(code: String): Result<Friend> {
        return runCatching {
            friendRepository.postInviteFriend(code)
        }
    }
}