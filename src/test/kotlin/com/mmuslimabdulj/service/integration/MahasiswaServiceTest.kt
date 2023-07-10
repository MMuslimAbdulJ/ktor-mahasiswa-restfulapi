package com.mmuslimabdulj.service.integration

import com.mmuslimabdulj.config.DatabaseFactory
import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.error.NotFoundException
import com.mmuslimabdulj.error.ValidationException
import com.mmuslimabdulj.model.CreateMahasiswaRequest
import com.mmuslimabdulj.model.UpdateMahasiswaRequest
import com.mmuslimabdulj.repository.MahasiswaRepository
import com.mmuslimabdulj.repository.impl.MahasiswaRepositoryImpl
import com.mmuslimabdulj.schema.Mahasiswas
import com.mmuslimabdulj.service.Impl.MahasiswaServiceImpl
import com.mmuslimabdulj.service.MahasiswaService
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.ktorm.database.Database
import org.ktorm.dsl.deleteAll
import org.ktorm.dsl.insert
import java.time.LocalDateTime
import kotlin.test.*

class MahasiswaServiceTest {

    lateinit var database : Database
    lateinit var mahasiswaRepository : MahasiswaRepository
    lateinit var mahasiswaService: MahasiswaService

    @Before
    fun setup() {
        database = DatabaseFactory.getDatabase()
        mahasiswaRepository = MahasiswaRepositoryImpl(database)
        mahasiswaService = MahasiswaServiceImpl(mahasiswaRepository)
        database.deleteAll(Mahasiswas)
    }

    @After
    fun after() {
        database.deleteAll(Mahasiswas)
    }

    @Test
    fun testGetAllMahasiswasNull() {
        val result = mahasiswaService.getAllMahasiswas()
        assertNull(result)
    }

    @Test
    fun testGetMahasiswasAllSuccess() {
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
        val result = mahasiswaService.getAllMahasiswas()
        assertNotNull(result)
        assertIs<List<Mahasiswa>>(result)
    }

    @Test
    fun testGetMahasiswaByNIMException() {
        assertThrows<ValidationException> {
            mahasiswaService.getMahasiswaByNim("123")
        }
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
        }
        val result = mahasiswaService.getMahasiswaByNim("217200035")
        assertNotNull(result)
        assertIs<Mahasiswa>(result)
    }

    @Test
    fun testCreateMahasiswaException() {
        val create = CreateMahasiswaRequest(
            nama = "1",
            nim = "",
            fakultas = "",
            prodi = "   "
        )
        assertThrows<ValidationException> {
            mahasiswaService.createMahasiswa(create)
        }
    }

    @Test
    fun testCreateMahasiswaSuccess() {
        val create = CreateMahasiswaRequest(
            nama = "Muhammad Muslim",
            nim = "217200025",
            fakultas = "Teknik",
            prodi = "Teknik Informatika"
        )
        val result = mahasiswaService.createMahasiswa(create)
        assertNotNull(result)
        assertIs<Mahasiswa>(result)
    }

    @Test
    fun testUpdateNIMException() {
        assertThrows<ValidationException> {
            val update = UpdateMahasiswaRequest(
                nama = "Muhammad Muslim",
                fakultas = "Teknik",
                prodi = "Teknik Informatika"
            )
            mahasiswaService.updateMahasiswa("123", update)
        }
    }

    @Test
    fun testUpdateBlankExceeption() {
        val update = UpdateMahasiswaRequest(
            nama = "            ",
            fakultas = "",
            prodi = "   "
        )
        assertThrows<ValidationException> {
            mahasiswaService.updateMahasiswa("217200035",update)
        }
    }

    @Test
    fun testUpdateMahasiswaSuccess() {
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
        }
        val update = UpdateMahasiswaRequest(
            nama = "Eren Yeager",
            fakultas = "Teknik",
            prodi = "Teknik Sipil"
        )
        val result = mahasiswaService.updateMahasiswa("217200035",update)
        assertNotNull(result)
        assertIs<Mahasiswa>(result)
        assertEquals(update.nama, result.nama)
    }

    @Test
    fun testDeleteNIMException() {
        assertThrows<ValidationException> {
            mahasiswaService.deleteMahasiswa("1")
        }
    }

    @Test
    fun testDeleteDeleteNotFoundException() {
        assertThrows<NotFoundException> {
            mahasiswaService.deleteMahasiswa("217200035")
        }
    }

    @Test
    fun testDeleteSuccess() {
        database.useTransaction {
            database.insert(Mahasiswas) {
                set(it.nama, "Muhammad Muslim")
                set(it.nim, "217200035")
                set(it.fakultas, "Teknik")
                set(it.prodi, "Teknik Informatika")
                set(it.createdAt, LocalDateTime.now())
                set(it.updatedAt, LocalDateTime.now())
            }
        }
        val result = mahasiswaService.deleteMahasiswa("217200035")
        assertTrue(result)
    }

}