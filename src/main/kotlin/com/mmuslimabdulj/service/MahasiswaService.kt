package com.mmuslimabdulj.service

import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.model.CreateMahasiswaRequest
import com.mmuslimabdulj.model.UpdateMahasiswaRequest

interface MahasiswaService {

    fun getAllMahasiswas() : List<Mahasiswa>?

    fun getMahasiswaByNim(nim : String) : Mahasiswa?

    fun createMahasiswa(createMahasiswaRequest: CreateMahasiswaRequest) : Mahasiswa?

    fun updateMahasiswa(nim : String, updateMahasiswaRequest: UpdateMahasiswaRequest) : Mahasiswa

    fun deleteMahasiswa(nim : String) : Boolean
}