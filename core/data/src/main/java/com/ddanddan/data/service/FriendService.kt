package com.ddanddan.data.service

import com.ddanddan.model.response.ResponseCheers
import com.ddanddan.model.response.ResponseFriends
import com.ddanddan.model.response.ResponseInviteCode
import com.ddanddan.model.response.ResponseInviteFriend
import com.ddanddan.model.response.ResponseInviterUser
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendService {
    @GET("/v1/friends/me")
    suspend fun getFriendsList(): ResponseFriends

    @POST("/v1/friends/invite-codes")
    suspend fun postInviteCode(): ResponseInviteCode

    @POST("/v1/friends/by-invite/{code}")
    suspend fun postInviteFriend(
        @Path("code") code: String
    ): ResponseInviteFriend

    @DELETE("/v1/friends/{friendId}")
    suspend fun deleteFriend(
        @Path("friendId") friendId: String
    ): Response<Unit>

    @POST("/v1/cheers/{friendId}")
    suspend fun postCheers(
        @Path("friendId") friendId: String
    ): ResponseCheers

    @GET("/v1/friends/invite-codes/{code}")
    suspend fun getFriendByCode(
        @Path("code") code: String
    ): ResponseInviterUser
}