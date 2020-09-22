package com.boltic28.networkretroroom.dagger

import com.boltic28.networkretroroom.data.network.NetworkService
import com.boltic28.networkretroroom.data.room.AppDataBase
import com.boltic28.networkretroroom.data.room.man.ManDao
import com.boltic28.networkretroroom.data.room.woman.WomanDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule(private val dataBase: AppDataBase) {

    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService = NetworkService()

    @Singleton
    @Provides
    fun provideManDao(): ManDao = dataBase.manDao()

    @Singleton
    @Provides
    fun provideWomanDao(): WomanDao = dataBase.womanDao()
}