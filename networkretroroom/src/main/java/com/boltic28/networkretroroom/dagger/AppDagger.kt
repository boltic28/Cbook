package com.boltic28.networkretroroom.dagger

import android.app.Application

class AppDagger: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        val dataModule = DataModule(this)
        val serviceModule = ServiceModule(dataModule.provideDataBase())

        component = DaggerAppComponent
            .builder()
            .createDataModule(dataModule)
            .createServiceModule(serviceModule)
            .buildComponent()
    }
}