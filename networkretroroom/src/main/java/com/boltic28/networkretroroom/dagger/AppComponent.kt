package com.boltic28.networkretroroom.dagger

import com.boltic28.networkretroroom.service.HumanServiceImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ServiceModule::class])
interface AppComponent {

    fun injectService(service: HumanServiceImpl)

    @Component.Builder
    interface DataBuilder{
        fun createDataModule(module: DataModule): DataBuilder
        fun createServiceModule(module: ServiceModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}