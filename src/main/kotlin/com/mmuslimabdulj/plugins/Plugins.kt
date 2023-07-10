package com.mmuslimabdulj.plugins

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.mmuslimabdulj.di.mahasiswaDependencies
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import org.koin.ktor.plugin.Koin

fun Application.plugins() {
    install(Koin) {
        modules(mahasiswaDependencies)
    }
    install(ContentNegotiation){
        jackson() {
            enable(SerializationFeature.INDENT_OUTPUT)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }
}