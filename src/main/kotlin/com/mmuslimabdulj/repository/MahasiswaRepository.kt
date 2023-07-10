package com.mmuslimabdulj.repository

import com.mmuslimabdulj.entity.Mahasiswa

interface MahasiswaRepository {

    fun getAllMahasiswas() : List<Mahasiswa>?

    fun getMahasiswaByNim(nim : String) : Mahasiswa?

    fun createMahasiswa(mahasiswa : Mahasiswa) : Mahasiswa

    fun updateMahasiswa(mahasiswa : Mahasiswa) : Mahasiswa

    fun deleteMahasiswa(nim : String) : Boolean

}