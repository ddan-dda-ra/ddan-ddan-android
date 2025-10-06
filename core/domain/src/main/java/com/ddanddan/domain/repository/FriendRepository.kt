package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Friend

interface FriendRepository {
    suspend fun getFriendsList(): List<Friend>
    suspend fun getInviteCode(): String
    suspend fun postInviteFriend(code: String): Friend
    suspend fun deleteFriend(fId: String): Unit
    suspend fun postCheers(fId: String): Unit
}