package com.mmuslimabdulj

import com.mmuslimabdulj.controller.mahasiswaController
import com.mmuslimabdulj.plugins.plugins
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        plugins()
        mahasiswaController()
    }.start(wait = true)
}