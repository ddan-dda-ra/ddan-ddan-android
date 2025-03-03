package com.ddanddan.ddanddan.data.model.response

import com.ddanddan.ddanddan.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    @SerialName("foodQuantity")
    val foodQuantity: Int? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("purposeCalorie")
    val purposeCalorie: Int? = null,
    @SerialName("toyQuantity")
    val toyQuantity: Int? = null
)

//todo - null 시 대체 값 문제될 여지가 없는지 확인 필요
fun ResponseUser.toUser(): User =
    User(
        foodQuantity = foodQuantity ?: 0,
        id = id ?: "",
        name = name ?: "",
        purposeCalorie = purposeCalorie ?: 0,
        toyQuantity = toyQuantity ?: 0
    )
