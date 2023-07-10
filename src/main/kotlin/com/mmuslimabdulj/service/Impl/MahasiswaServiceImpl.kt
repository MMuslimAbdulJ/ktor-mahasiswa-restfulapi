package com.mmuslimabdulj.service.Impl

import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.error.NotFoundException
import com.mmuslimabdulj.error.ValidationException
import com.mmuslimabdulj.model.CreateMahasiswaRequest
import com.mmuslimabdulj.model.UpdateMahasiswaRequest
import com.mmuslimabdulj.repository.MahasiswaRepository
import com.mmuslimabdulj.service.MahasiswaService
import io.ktor.server.application.*
import java.time.LocalDateTime

class MahasiswaServiceImpl(private val mahasiswaRepository : MahasiswaRepository) : MahasiswaService {
    override fun getAllMahasiswas(): List<Mahasiswa>? {
        return this.mahasiswaRepository.getAllMahasiswas()
    }

    override fun getMahasiswaByNim(nim: String): Mahasiswa? {
        if(nim.length != 9 || !nim.matches(Regex("\\d+"))) throw ValidationException("Invalid NIM")
        return this.mahasiswaRepository.getMahasiswaByNim(nim)
    }

    override fun createMahasiswa(createMahasiswaRequest: CreateMahasiswaRequest): Mahasiswa {
        val properties = arrayOf(
            createMahasiswaRequest.nama,
            createMahasiswaRequest.nim,
            createMahasiswaRequest.fakultas,
            createMahasiswaRequest.prodi,
        )
        if (properties.any { it.isBlank() }) throw ValidationException("Create Request is invalid")
        if (createMahasiswaRequest.nim.length != 9 || !createMahasiswaRequest.nim.matches(Regex("\\d+"))) throw ValidationException("NIM is invalid")
        val mahasiswa = Mahasiswa {
            nama = createMahasiswaRequest.nama
            nim = createMahasiswaRequest.nim
            fakultas = createMahasiswaRequest.fakultas
            prodi = createMahasiswaRequest.prodi
            createdAt = LocalDateTime.now().withNano(0)
            updatedAt = LocalDateTime.now().withNano(0)
        }
        return this.mahasiswaRepository.createMahasiswa(mahasiswa)
    }

    override fun updateMahasiswa(nim: String, updateMahasiswaRequest: UpdateMahasiswaRequest): Mahasiswa {
        if(nim.length != 9 || !nim.matches(Regex("\\d+"))) throw ValidationException("NIM is invalid")
        val properties = arrayOf(
            updateMahasiswaRequest.nama,
            updateMahasiswaRequest.fakultas,
            updateMahasiswaRequest.prodi,
        )
        if (properties.any { it.isBlank() }) throw ValidationException("Create Request cannot be null or blank")
        val mahasiswa = this.mahasiswaRepository.getMahasiswaByNim(nim) ?: throw NotFoundException("User doesn't exist")
        mahasiswa.apply {
            nama = updateMahasiswaRequest.nama
            fakultas = updateMahasiswaRequest.fakultas
            prodi = updateMahasiswaRequest.prodi
            updatedAt = LocalDateTime.now().withNano(0)
        }
        return this.mahasiswaRepository.updateMahasiswa(mahasiswa)
    }

    override fun deleteMahasiswa(nim: String): Boolean {
        if(nim.length != 9 || !nim.matches(Regex("\\d+"))) throw ValidationException("NIM is invalid")
        mahasiswaRepository.getMahasiswaByNim(nim) ?: throw NotFoundException("Delete is failed, because Mahasiswa data doesn't exists")
        return this.mahasiswaRepository.deleteMahasiswa(nim)
    }

}