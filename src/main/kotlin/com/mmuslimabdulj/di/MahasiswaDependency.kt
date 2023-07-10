package com.mmuslimabdulj.di

import com.mmuslimabdulj.config.DatabaseFactory
import com.mmuslimabdulj.repository.MahasiswaRepository
import com.mmuslimabdulj.repository.impl.MahasiswaRepositoryImpl
import com.mmuslimabdulj.service.Impl.MahasiswaServiceImpl
import com.mmuslimabdulj.service.MahasiswaService
import org.koin.dsl.module

val mahasiswaDependencies = module {
    single<MahasiswaRepository> { MahasiswaRepositoryImpl(DatabaseFactory.getDatabase()) }
    single<MahasiswaService> { MahasiswaServiceImpl(get()) }
}