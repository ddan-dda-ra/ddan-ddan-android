package com.ddanddan.ddanddan.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.HttpException


data class BaseErrorResponse(
    val code: String,
    val message: String,
    val data: JsonElement? = null
)

fun HttpException.toBaseErrorResponse(): BaseErrorResponse? {
    val errorBody = this.response()?.errorBody()
    val errorString = errorBody?.string()

    return try {
        Gson().fromJson(errorString, BaseErrorResponse::class.java)
    } catch (e: Exception) {
        null
    }
}