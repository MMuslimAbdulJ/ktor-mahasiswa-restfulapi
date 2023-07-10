package com.mmuslimabdulj.schema

import com.mmuslimabdulj.entity.Mahasiswa
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Mahasiswas : Table<Mahasiswa>(tableName = "mahasiswas") {
    val id = int("id").primaryKey().bindTo { it.id }
    val nama = varchar("nama").bindTo { it.nama }
    val nim = varchar("nim").primaryKey().bindTo { it.nim }
    val fakultas = varchar("fakultas").bindTo { it.fakultas }
    val prodi = varchar("prodi").bindTo { it.prodi }
    val createdAt = datetime("createdAt").bindTo { it.createdAt }
    val updatedAt = datetime("updatedAt").bindTo { it.updatedAt }
}