package com.mmuslimabdulj.model

import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.NotNull

@Serializable
data class CreateMahasiswaRequest(

    @NotNull
    val nama: String,

    @NotNull
    @Pattern("[0-9]+")
    @Size(min = 9, max = 9)
    val nim: String,

    @NotNull
    val fakultas: String,

    @NotNull
    val prodi: String
)
