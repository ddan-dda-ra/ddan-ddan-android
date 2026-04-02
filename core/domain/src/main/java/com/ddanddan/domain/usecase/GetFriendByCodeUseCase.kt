package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendByCodeUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(code: String): Result<Friend> = runCatching {
        friendRepository.getFriendByCode(code)
    }
}