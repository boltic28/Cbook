package com.boltic28.networkretroroom.room.woman

import androidx.room.*

@Dao
interface WomanDao {

    @Insert
    fun insert(womanEntity: WomanEntity): Long

    @Update
    fun update(womanEntity: WomanEntity): Int

    @Delete
    fun delete(womanEntity: WomanEntity): Int

    @Query("SELECT * FROM man ORDER BY name ASC")
    fun getAll(): List<WomanEntity>

    @Query("SELECT * FROM man WHERE id = :id")
    fun getById(id: Long): WomanEntity
}