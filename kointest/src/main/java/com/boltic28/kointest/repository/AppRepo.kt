package com.boltic28.kointest.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boltic28.kointest.repository.user.UserDao
import com.boltic28.kointest.repository.user.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppRepo: RoomDatabase() {

    companion object{
        const val DB_NAME = "koin_db"
    }

    abstract fun userDao(): UserDao

    override fun toString(): String = "database is injected"
}