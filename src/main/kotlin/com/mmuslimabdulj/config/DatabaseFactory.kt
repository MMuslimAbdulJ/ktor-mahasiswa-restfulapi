package com.mmuslimabdulj.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.ktorm.database.Database


object DatabaseFactory {

    private lateinit var database: Database
    val dotenv : Dotenv = dotenv()

    init {
        this.init()
    }
        fun init() {
            try {
                val config = HikariConfig().apply {
                    driverClassName = "com.mysql.cj.jdbc.Driver"
                    jdbcUrl = "jdbc:mysql://localhost:3306/${dotenv["DATABASE"]}"
                    username = dotenv["DATABASE_USERNAME"]
                    password = dotenv["DATABASE_PASSWORD"]
                }
                val dataSource = HikariDataSource(config)
                this.database = Database.connect(dataSource)
            }catch (exception : Exception) {
                println("Terjadi kesalahan saat menghubungkan ke database: ${exception.message}")
            }
        }

    fun getDatabase() : Database {
        return this.database
    }

}
