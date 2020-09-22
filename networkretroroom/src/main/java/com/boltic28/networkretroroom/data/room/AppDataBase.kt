package com.boltic28.networkretroroom.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boltic28.networkretroroom.data.room.man.ManDao
import com.boltic28.networkretroroom.data.room.man.ManEntity
import com.boltic28.networkretroroom.data.room.woman.WomanDao
import com.boltic28.networkretroroom.data.room.woman.WomanEntity

@Database(
    entities = [ManEntity::class, WomanEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DB_NAME = "human_book"
    }

    abstract fun manDao(): ManDao
    abstract fun womanDao(): WomanDao
}