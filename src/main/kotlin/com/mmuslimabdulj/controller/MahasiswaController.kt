package com.mmuslimabdulj.controller

import com.mmuslimabdulj.entity.Mahasiswa
import com.mmuslimabdulj.model.*
import com.mmuslimabdulj.model.Map
import com.mmuslimabdulj.service.MahasiswaService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.mahasiswaController() {
    val mahasiswaService by inject<MahasiswaService>()
    routing {
        get("/api/mahasiswa") {
            val mahasiswas: List<Mahasiswa>? = mahasiswaService.getAllMahasiswas()
            val maps: List<Map>? = mahasiswas?.map {
                Map(
                    nama = it.nama,
                    nim = it.nim,
                    fakultas = it.fakultas,
                    prodi = it.prodi,
                    createdAt = it.createdAt.toString(),
                    updatedAt = it.updatedAt.toString()
                )
            }
            if (mahasiswas != null) if (mahasiswas.isEmpty()) {
                val responseSuccess = ResponseSuccess(
                    code = HttpStatusCode.NotFound.value,
                    status = "Not Found",
                    data = arrayOf<String>()
                )
                call.respond(HttpStatusCode.NotFound, responseSuccess)
            } else {
                val responseSuccess = ResponseSuccess(code = 200, status = "OK", data = maps)
                call.respond(HttpStatusCode.OK, responseSuccess)
            }
        }

        get("/api/mahasiswa/{nim}") {
            try {
                val nim = call.parameters["nim"]!!
                val mahasiswa = mahasiswaService.getMahasiswaByNim(nim)
                if (mahasiswa == null) {
                    call.respond(HttpStatusCode.NotFound)
                }
                val map = mahasiswa?.let { it ->
                    Map(
                        nama = it.nama,
                        nim = mahasiswa.nim,
                        fakultas = mahasiswa.fakultas,
                        prodi = mahasiswa.prodi,
                        createdAt = mahasiswa.createdAt.toString(),
                        updatedAt = mahasiswa.updatedAt.toString()
                    )
                }
                val responseSuccess = ResponseSuccess(
                    code = HttpStatusCode.OK.value,
                    status = "OK",
                    data = map
                )
                call.respond(HttpStatusCode.OK, responseSuccess)
            } catch (exception: Exception) {
                val responseError = ResponseError(
                    code = HttpStatusCode.BadRequest.value,
                    status = "Bad Request",
                    error = exception.message.toString()
                )
                call.respond(HttpStatusCode.BadRequest, responseError)
            }
        }

        post("/api/mahasiswa") {
            try {
                val request = call.receive<CreateMahasiswaRequest>()
                val mahasiswa = mahasiswaService.createMahasiswa(request)
                val map = mahasiswa?.let { it ->
                    Map(
                        nama = it.nama,
                        nim = mahasiswa.nim,
                        fakultas = mahasiswa.fakultas,
                        prodi = mahasiswa.prodi,
                        createdAt = mahasiswa.createdAt.toString(),
                        updatedAt = mahasiswa.updatedAt.toString()
                    )
                }
                val response = ResponseSuccess(
                    code = HttpStatusCode.Created.value,
                    status = "Created",
                    data = map
                )
                call.respond(HttpStatusCode.Created, response)
            } catch (exception: Exception) {
                val responseError = ResponseError(
                    code = HttpStatusCode.UnprocessableEntity.value,
                    status = "Unprocessable Entity",
                    error = exception.message.toString()
                )
                call.respond(HttpStatusCode.UnprocessableEntity, responseError)
            }
        }

        patch("api/mahasiswa/{nim}") {
            try {
                val nim = call.parameters["nim"] ?: throw Exception("Please insert the NIM")
                val request = call.receive<UpdateMahasiswaRequest>()
                val updatedMahasiswa = mahasiswaService.updateMahasiswa(nim, request)
                val response = ResponseSuccess(
                    code = HttpStatusCode.OK.value,
                    status = "Updated",
                    data = updatedMahasiswa
                )
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                val responseError = ResponseError(
                    code = HttpStatusCode.BadRequest.value,
                    status = "Bad Request",
                    error = exception.message.toString()
                )
                call.respond(HttpStatusCode.UnprocessableEntity, responseError)
            }
        }

        delete("api/mahasiswa/{nim}") {
            try {
                val nim = call.parameters["nim"] ?: throw IllegalArgumentException("Please insert the NIM")
                mahasiswaService.deleteMahasiswa(nim)
                call.respond(HttpStatusCode.NoContent)
            }catch (exception : IllegalArgumentException) {
                val responseError = ResponseError(
                    code = HttpStatusCode.BadRequest.value,
                    status = "Bad Request",
                    error = exception.message.toString()
                )
                call.respond(HttpStatusCode.NotFound, responseError)
            }catch (exception : Exception) {
                val responseError = ResponseError(
                    code = HttpStatusCode.NotFound.value,
                    status = "Not Found",
                    error = exception.message.toString()
                )
                call.respond(HttpStatusCode.NotFound, responseError)
            }
        }
    }
}

