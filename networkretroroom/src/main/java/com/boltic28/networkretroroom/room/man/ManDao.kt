package com.boltic28.networkretroroom.room.man

import androidx.room.*

@Dao
interface ManDao {

    @Insert
    fun insert(manEntity: ManEntity): Long

    @Update
    fun update(manEntity: ManEntity): Int

    @Delete
    fun delete(manEntity: ManEntity): Int

    @Query("SELECT * FROM man ORDER BY name ASC")
    fun getAll(): List<ManEntity>

    @Query("SELECT * FROM man WHERE id = :id")
    fun getById(id: Long): ManEntity
}