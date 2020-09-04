package com.boltic28.cbook.dagger

import com.boltic28.cbook.data.DataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDataBase(): DataBase{
        return DataBase()
    }
}