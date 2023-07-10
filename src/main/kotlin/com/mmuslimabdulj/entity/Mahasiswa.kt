package com.mmuslimabdulj.entity

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Mahasiswa : Entity<Mahasiswa> {
    companion object : Entity.Factory<Mahasiswa>()
    val id : Int
    var nama: String
    var nim: String
    var fakultas: String
    var prodi: String
    var createdAt: LocalDateTime
    var updatedAt: LocalDateTime
}