package com.mmuslimabdulj.model

import kotlinx.serialization.Serializable

@Serializable
data class Map(
    var nama : String,
    var nim : String,
    var fakultas : String,
    var prodi : String,
    var createdAt : String,
    var updatedAt : String,
)
