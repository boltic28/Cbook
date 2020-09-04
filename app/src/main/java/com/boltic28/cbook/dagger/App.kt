package com.boltic28.cbook.dagger

import android.app.Application

class App: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        val dataBaseModule = DataModule()

        component = DaggerAppComponent
            .builder()
            .createDataModule(dataBaseModule)
            .buildComponent()
    }
}