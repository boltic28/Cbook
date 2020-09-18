package com.boltic28.networkretroroom.dagger

import com.boltic28.networkretroroom.room.AppDataBase
import com.boltic28.networkretroroom.room.human.HumanDao
import com.boltic28.networkretroroom.service.HumanServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule(private val dataBase: AppDataBase) {

    @Singleton
    @Provides
    fun provideHumanService(): HumanServiceImpl {
        return HumanServiceImpl()
    }

    @Singleton
    @Provides
    fun provideHumanDao(): HumanDao {
        return dataBase.humanDao()
    }
}