package com.boltic28.networkretroroom.dagger

import com.boltic28.networkretroroom.presentation.view.MainActivity
import com.boltic28.networkretroroom.data.room.man.ManServiceImpl
import com.boltic28.networkretroroom.data.room.woman.WomanServiceImpl
import com.boltic28.networkretroroom.presentation.model.MainModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ServiceModule::class])
interface AppComponent {

    fun injectService(service: ManServiceImpl)
    fun injectService(service: WomanServiceImpl)
    fun injectModel(model: MainModel)

    fun injectActivity(activity: MainActivity)

    @Component.Builder
    interface DataBuilder{
        fun createDataModule(module: DataModule): DataBuilder
        fun createServiceModule(module: ServiceModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}