package com.boltic28.networkretroroom.dagger

import android.content.Context
import com.boltic28.networkretroroom.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDataBase(): AppDataBase = AppDataBase.getInstance(context)
}