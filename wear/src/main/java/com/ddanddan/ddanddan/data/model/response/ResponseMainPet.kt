package com.ddanddan.ddanddan.data.model.response

import com.ddanddan.ddanddan.domain.entity.MainPet
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMainPet(
    @SerialName("mainPet")
    val mainPet: MainPet? = null
) {
    @Serializable
    data class MainPet(
        @SerialName("expPercent")
        val expPercent: Double? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("level")
        val level: Int? = null,
        @SerialName("type")
        val type: String? = null
    )
}

//todo - null 시 대체 값 문제될 여지가 없는지 확인 필요
fun ResponseMainPet.toMainPet(): MainPet =
    MainPet(
        expPercent = this.mainPet?.expPercent ?: 0.0,
        id = this.mainPet?.id ?: "",
        level = this.mainPet?.level ?: 0,
        type = this.mainPet?.type ?: ""
    )