package com.mmuslimabdulj.repository.integration

import com.mmuslimabdulj.config.DatabaseFactory
import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.repository.impl.MahasiswaRepositoryImpl
import com.mmuslimabdulj.schema.Mahasiswas
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.assertThrows
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.sql.SQLIntegrityConstraintViolationException
import java.time.LocalDateTime

class MahasiswaRepositoryImplTest {

    lateinit var database: Database

    lateinit var mahasiswaRepository: MahasiswaRepositoryImpl

    @Before
    fun setup() {
        database = DatabaseFactory.getDatabase()
        mahasiswaRepository = MahasiswaRepositoryImpl(database)
        database.deleteAll(Mahasiswas)
    }

    @After
    fun teardown() {
        database.deleteAll(Mahasiswas)
    }

    @Test
    fun testGetAllMahasiswasSuccess() {
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
            database.insert(Mahasiswas) {
                set(it.nama, "Eren Yeager")
                set(it.nim, "217200055")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Sipil")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
        }
        val result = mahasiswaRepository.getAllMahasiswas()
        result?.forEach {
            println(it)
        }
        assertNotNull(result)
    }

    @Test
    fun testGetAllMahasiswasNull() {
        val result = mahasiswaRepository.getAllMahasiswas()
        assertNull(result)
    }

    @Test
    fun testGetMahasiswaByNIMSuccess() {
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
            database.insert(Mahasiswas) {
                set(it.nama, "Eren Yeager")
                set(it.nim, "217200055")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Sipil")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
        }
        val result = mahasiswaRepository.getMahasiswaByNim("217200055")
        println(result)
        assertNotNull(result)
    }

    @Test
    fun testGetMahasiswaByNIMNull() {
        val result = mahasiswaRepository.getMahasiswaByNim("null")
        assertNull(result)
    }

    @Test
    fun testCreateMahasiswaSuccess() {
        val mahasiswa = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        val result = mahasiswaRepository.createMahasiswa(mahasiswa)
        println(result)
        assertEquals(mahasiswa, result)
    }

    @Test
    fun testCreateMahasiswaException() {
        /*
        * if nim field duplicate
        * */
        val now = LocalDateTime.now().withNano(0)
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, now)
                set(it.updatedAt, now)
            }
        }
        val mahasiswa = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = now
            updatedAt = now
        }
        assertThrows<SQLIntegrityConstraintViolationException> {
            var result : Mahasiswa? = mahasiswaRepository.createMahasiswa(mahasiswa)
            assertNull(result)
        }
    }

    @Test
    fun testUpdateSuccess() {
        val now = LocalDateTime.now().withNano(0)
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, now)
                set(it.updatedAt, now)
            }
        }
        val mahasiswa = Mahasiswa {
            nama = "Eren Yeager"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Sipil"
            createdAt = now
            updatedAt = now
        }
        val result = mahasiswaRepository.updateMahasiswa(mahasiswa)
        assertEquals(mahasiswa, result)
    }

    @Test
    fun testDeleteSuccess() {
        val now = LocalDateTime.now().withNano(0)
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, now)
                set(it.updatedAt, now)
            }
        }
        val result = mahasiswaRepository.deleteMahasiswa("217200035")
        assertTrue(result)
    }

    @Test
    fun testDeleteFailed() {
        val result = mahasiswaRepository.deleteMahasiswa("notfound")
        assertFalse(result)
    }

}