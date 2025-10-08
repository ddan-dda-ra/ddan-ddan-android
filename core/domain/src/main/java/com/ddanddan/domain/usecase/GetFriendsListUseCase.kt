package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendsListUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): Result<List<Friend>> {
        return runCatching {
            friendRepository.getFriendsList()
        }
    }
}