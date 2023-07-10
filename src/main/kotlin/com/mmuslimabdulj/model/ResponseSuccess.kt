package com.mmuslimabdulj.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSuccess<T>(
    val code: Int,
    val status: String,
    val data: T
)