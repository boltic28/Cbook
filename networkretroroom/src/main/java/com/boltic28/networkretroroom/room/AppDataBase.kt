package com.boltic28.networkretroroom.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boltic28.networkretroroom.room.man.ManDao
import com.boltic28.networkretroroom.room.man.ManEntity
import com.boltic28.networkretroroom.room.woman.WomanDao
import com.boltic28.networkretroroom.room.woman.WomanEntity

@Database(
    entities = [ManEntity::class, WomanEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "human_book"

        fun getInstance(context: Context): AppDataBase {
            return Room
                .databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                .build()
        }
    }

    abstract fun manDao(): ManDao
    abstract fun womanDao(): WomanDao
}