package com.mmuslimabdulj.model

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class UpdateMahasiswaRequest (

    @NotNull
    @NotBlank
    val nama : String,

    @NotNull
    @NotBlank
    val fakultas : String,

    @NotNull
    @NotBlank
    val prodi : String

)