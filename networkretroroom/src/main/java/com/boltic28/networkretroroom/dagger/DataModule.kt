package com.boltic28.networkretroroom.dagger

import android.content.Context
import androidx.room.Room
import com.boltic28.networkretroroom.util.NetworkChecker
import com.boltic28.networkretroroom.data.room.AppDataBase
import com.boltic28.networkretroroom.util.Messenger
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

    @Singleton
    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(context)

    @Singleton
    @Provides
    fun provideMessenger(): Messenger =
        Messenger(context)
}