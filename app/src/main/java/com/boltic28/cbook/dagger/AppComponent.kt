package com.boltic28.cbook.dagger

import com.boltic28.cbook.data.DataBase
import com.boltic28.cbook.presentation.*
import com.boltic28.cbook.service.ContactService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun injectModel(fragment: MainFragmentModel)
    fun injectModel(fragment: ContactFragmentModel)
    fun injectModel(fragment: MainActivityModel)

    fun injectFragment(model: MainFragment)
    fun injectFragment(model: ContactFragment)

    fun injectActivity(activity: MainActivity)

    fun injectService(service: ContactService)

    @Component.Builder
    interface DataBuilder{
        fun createDataModule(module: DataModule): DataBuilder
        fun buildComponent(): AppComponent
    }
}