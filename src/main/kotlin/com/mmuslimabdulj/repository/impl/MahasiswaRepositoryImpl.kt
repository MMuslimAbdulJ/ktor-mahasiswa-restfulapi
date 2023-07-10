package com.mmuslimabdulj.repository.impl


import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.repository.MahasiswaRepository
import com.mmuslimabdulj.schema.Mahasiswas
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.singleOrNull


class MahasiswaRepositoryImpl(val database: Database) : MahasiswaRepository {

    override fun getAllMahasiswas(): List<Mahasiswa>? {
        val listMahasiswas: List<Mahasiswa> = database.from(Mahasiswas)
            .select()
            .map {
                Mahasiswas.createEntity(it)
            }
        return listMahasiswas.ifEmpty { null }
    }

    override fun getMahasiswaByNim(nim: String): Mahasiswa? {
        return database.sequenceOf(Mahasiswas)
            .filter {
                it.nim eq nim
            }
            .singleOrNull()
    }

    override fun createMahasiswa(mahasiswa: Mahasiswa): Mahasiswa {
        val rowCount = database.useTransaction {
             database.insert(Mahasiswas) {
                set(it.nama, mahasiswa.nama)
                set(it.nim, mahasiswa.nim)
                set(it.fakultas, mahasiswa.fakultas)
                set(it.prodi, mahasiswa.prodi)
                set(it.createdAt, mahasiswa.createdAt)
                set(it.updatedAt, mahasiswa.updatedAt)
            }
        }
        return if (rowCount > 0) mahasiswa else throw Exception("Insert is failed")
    }

    override fun updateMahasiswa(mahasiswa: Mahasiswa): Mahasiswa {
        val rowCount = database.useTransaction {
            database.update(Mahasiswas) {
                set(it.nama, mahasiswa.nama)
                set(it.fakultas, mahasiswa.fakultas)
                set(it.prodi, mahasiswa.prodi)
                set(it.updatedAt, mahasiswa.updatedAt)
                where { it.nim eq mahasiswa.nim }
            }
        }
        return if (rowCount > 0) mahasiswa else throw Exception("Update is failed")
    }

    override fun deleteMahasiswa(nim: String): Boolean {
        return database.useTransaction {
            database.delete(Mahasiswas) {
                it.nim eq nim
            }
        } > 0
    }
}