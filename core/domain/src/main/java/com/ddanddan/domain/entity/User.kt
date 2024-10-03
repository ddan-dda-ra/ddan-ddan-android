package com.ddanddan.domain.entity

data class User(
    val id: String,
    val name: String?,
    val purposeCalorie: Int,
    val foodQuantity: Int,
    val toyQuantity: Int
)