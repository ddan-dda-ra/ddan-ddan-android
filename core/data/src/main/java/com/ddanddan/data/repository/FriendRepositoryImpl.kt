package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteFriendDataSource
import com.ddanddan.domain.entity.Friend
import com.ddanddan.domain.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendDataSource: RemoteFriendDataSource
) : FriendRepository {
    override suspend fun getFriendsList(): List<Friend> {
        return friendDataSource.getFriendsList().map { it.toFriend()}
    }

    override suspend fun getInviteCode(): String {
        return friendDataSource.getInviteCode()
    }

    override suspend fun postInviteFriend(code: String): Friend {
        return friendDataSource.postInviteFriend(code).toFriend()
    }

    override suspend fun deleteFriend(fId: String) {
        friendDataSource.deleteFriend(fId)
    }

    override suspend fun postCheers(fId: String) {
        friendDataSource.postCheers(fId)
    }
}