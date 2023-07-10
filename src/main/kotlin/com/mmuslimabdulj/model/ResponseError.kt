package com.mmuslimabdulj.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError<T>(
    val code: Int,
    val status: String,
    val error: T
)
