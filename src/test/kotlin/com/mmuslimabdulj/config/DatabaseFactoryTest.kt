package com.mmuslimabdulj.config

import com.mmuslimabdulj.config.DatabaseFactory
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull

class DatabaseFactoryTest {

    @Test
    fun testConnectionNotNull() {
        val connection = DatabaseFactory.init()
        val database = DatabaseFactory.getDatabase()
        assertNotNull(connection,"Database Null!")
        assertNotNull(database,"Database Null!")
    }

}