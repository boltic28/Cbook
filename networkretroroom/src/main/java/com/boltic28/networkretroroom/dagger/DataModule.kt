package com.boltic28.networkretroroom.dagger

import android.content.Context
import androidx.room.Room
import com.boltic28.networkretroroom.data.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDataBase(): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        AppDataBase.DB_NAME
    ).build()

}