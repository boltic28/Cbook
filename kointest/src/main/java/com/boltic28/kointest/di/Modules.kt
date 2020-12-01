package com.boltic28.kointest.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boltic28.kointest.PermissionChecker
import com.boltic28.kointest.repository.AppRepo
import com.boltic28.kointest.repository.user.UserDao
import com.boltic28.kointest.repository.user.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val applicationModule = module(override = true) {

    single {
        PermissionChecker(androidContext())
    }

    single<AppRepo> {
        Room.databaseBuilder(
            androidContext(),
            AppRepo::class.java, AppRepo.DB_NAME
        ).build()
    }

    single<UserDao> { get<AppRepo>().userDao() }

    single<UserRepository> { UserRepository(get<UserDao>()) }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "SharedPreferences",
            Context.MODE_PRIVATE
        )
    }
}