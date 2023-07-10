package com.mmuslimabdulj.service.unit

import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.error.NotFoundException
import com.mmuslimabdulj.error.ValidationException
import com.mmuslimabdulj.model.CreateMahasiswaRequest
import com.mmuslimabdulj.model.UpdateMahasiswaRequest
import com.mmuslimabdulj.repository.MahasiswaRepository
import com.mmuslimabdulj.service.Impl.MahasiswaServiceImpl
import com.mmuslimabdulj.service.MahasiswaService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.time.LocalDateTime
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MahasiswaServiceTest {

    val mahasiswaRepository : MahasiswaRepository = Mockito.mock(MahasiswaRepository::class.java)

    val mahasiswaService : MahasiswaService = MahasiswaServiceImpl(this.mahasiswaRepository)

    @Test
    fun testGetAllMahasiswasSuccess() {
        val mahasiswas : List<Mahasiswa> = listOf(
            Mahasiswa {
                nama = "Muhammad Muslim"
                nim = "217200035"
                fakultas = "Teknik"
                prodi = "Teknik Informatika"
                createdAt = LocalDateTime.now().withNano(0)
                updatedAt = LocalDateTime.now().withNano(0)
            },
            Mahasiswa {
                nama = "Eren Yeager"
                nim = "217200055"
                fakultas = "Teknik"
                prodi = "Teknik Sipil"
                createdAt = LocalDateTime.now().withNano(0)
                updatedAt = LocalDateTime.now().withNano(0)
            }
        )
        Mockito.`when`(mahasiswaRepository.getAllMahasiswas()).thenReturn(mahasiswas)
        val result = mahasiswaService.getAllMahasiswas()
        assertEquals(mahasiswas, result)
        Mockito.verify(mahasiswaRepository, Mockito.times(1)).getAllMahasiswas()
    }

    @Test
    fun testGetAllMahasiswasNull() {
        Mockito.`when`(mahasiswaRepository.getAllMahasiswas()).thenReturn(null)
        val result = mahasiswaService.getAllMahasiswas()
        assertNull(result)
        Mockito.verify(mahasiswaRepository, Mockito.times(1)).getAllMahasiswas()
    }

    @Test
    fun testGetMahasiswaByNIMSuccess() {
        val mahasiswa = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        Mockito.`when`(mahasiswaRepository.getMahasiswaByNim("217200035")).thenReturn(mahasiswa)
        val result = mahasiswaService.getMahasiswaByNim("217200035")
        assertEquals(mahasiswa, result)
    }

    @Test
    fun testGetMahasiswaException() {
        assertThrows<ValidationException> {
            mahasiswaService.getMahasiswaByNim("123")
        }
    }

    @Test
    fun testCreateMahasiswaSuccess() {
        val createMahasiswa = CreateMahasiswaRequest(
            nim = "217200035",
            nama = "Muhammad Muslim",
            fakultas = "Teknik",
            prodi = "Teknik Informatika",
        )
        val mahasiswa = Mahasiswa {
            nama = createMahasiswa.nama
            nim = createMahasiswa.nim
            fakultas = createMahasiswa.fakultas
            prodi = createMahasiswa.prodi
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        val mahasiswaCreated = mahasiswa.copy()
        Mockito.`when`(mahasiswaRepository.createMahasiswa(mahasiswa)).thenReturn(mahasiswaCreated)
        val result = mahasiswaService.createMahasiswa(createMahasiswa)
        assertEquals(mahasiswa, mahasiswaCreated)
        Mockito.verify(mahasiswaRepository, Mockito.times(1)).createMahasiswa(mahasiswa)
    }

    @Test
    fun testCreateMahasiswaValidationBlank() {
        val createMahasiswa = CreateMahasiswaRequest(
            nim = "   ",
            nama = "  ",
            fakultas ="   ",
            prodi = "   ",
        )
        assertThrows<ValidationException> {
            mahasiswaService.createMahasiswa(createMahasiswa)
        }
    }

    @Test
    fun testCreateMahasiswaValidationNIM() {
        val createMahasiswa = CreateMahasiswaRequest(
            nim = "123",
            nama = "Muhammad Muslim",
            fakultas ="Teknik",
            prodi = "Teknik Informatika",
        )
        assertThrows<ValidationException> {
            mahasiswaService.createMahasiswa(createMahasiswa)
        }
    }

    @Test
    fun testUpdateSuccess() {
        val updateMahasiswaRequest = UpdateMahasiswaRequest(
            nama = "Eren Yeager",
            fakultas = "Teknik",
            prodi = "Teknik Sipil",
        )
        val mahasiswaInDb = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        val update = mahasiswaInDb.copy()
        update.apply {
            nama = updateMahasiswaRequest.nama
            nim = "217200035"
            fakultas = updateMahasiswaRequest.fakultas
            prodi = updateMahasiswaRequest.prodi
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        val mahasiswaUpdated = update.copy()
        Mockito.`when`(mahasiswaRepository.getMahasiswaByNim("217200035")).thenReturn(mahasiswaInDb)
        Mockito.`when`(mahasiswaRepository.updateMahasiswa(update)).thenReturn(mahasiswaUpdated)
        val result = mahasiswaService.updateMahasiswa("217200035",updateMahasiswaRequest)
        assertEquals(mahasiswaUpdated, result)
        Mockito.verify(mahasiswaRepository, Mockito.times(1)).getMahasiswaByNim("217200035")
        Mockito.verify(mahasiswaRepository, Mockito.times(1)).updateMahasiswa(update)
    }

    @Test
    fun testUpdateNIMException() {
        val update = UpdateMahasiswaRequest(
            nama = "Eren Yeager",
            fakultas = "Teknik",
            prodi = "Teknik Sipil",
        )
        assertThrows<ValidationException> {
            mahasiswaService.updateMahasiswa("123", update)
        }
    }

    @Test
    fun testUpdateNotFoundException() {
        val update = UpdateMahasiswaRequest(
            nama = "Eren Yeager",
            fakultas = "Teknik",
            prodi = "Teknik Sipil",
        )
        assertThrows<NotFoundException> {
            mahasiswaService.updateMahasiswa("217200035", update)
        }
    }

    @Test
    fun testDeleteSuccess() {
        val mahasiswaInDb = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        Mockito.`when`(mahasiswaRepository.getMahasiswaByNim("217200035")).thenReturn(mahasiswaInDb)
        Mockito.`when`(mahasiswaRepository.deleteMahasiswa("217200035")).thenReturn(true)
        val result = mahasiswaService.deleteMahasiswa("217200035")
        assertTrue(result)
    }

    @Test
    fun testDeleteFailed() {
        val mahasiswaInDb = Mahasiswa {
            nama = "Muhammad Muslim"
            nim = "217200035"
            fakultas = "Teknik"
            prodi = "Teknik Informatika"
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        Mockito.`when`(mahasiswaRepository.getMahasiswaByNim("217200035")).thenReturn(mahasiswaInDb)
        Mockito.`when`(mahasiswaRepository.deleteMahasiswa("217200035")).thenReturn(false)
        val result = mahasiswaService.deleteMahasiswa("217200035")
        assertFalse(result)
    }

    @Test
    fun testDeleteException() {
        assertThrows<Exception> {
            mahasiswaService.deleteMahasiswa("217200035")
        }
    }

}