package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.FriendService
import com.ddanddan.model.response.ResponseFriend
import javax.inject.Inject

class RemoteFriendDataSource @Inject constructor(
    private val friendService: FriendService
) {
    suspend fun getFriendsList(): List<ResponseFriend> = friendService.getFriendsList().friends
    suspend fun getInviteCode(): String = friendService.postInviteCode().code
    suspend fun postInviteFriend(code: String): ResponseFriend = friendService.postInviteFriend(code).friendUser
    suspend fun deleteFriend(fId: String) = friendService.deleteFriend(fId)
    suspend fun postCheers(fId: String) = friendService.postCheers(fId)
    suspend fun getFriendByCode(code: String): ResponseFriend = friendService.getFriendByCode(code).inviterUser
}