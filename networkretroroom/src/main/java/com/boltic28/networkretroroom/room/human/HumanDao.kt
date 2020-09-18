package com.boltic28.networkretroroom.room.human

import androidx.room.*

@Dao
interface HumanDao {

    @Insert
    fun insert(human: Human): Long

    @Update
    fun update(human: Human): Int

    @Delete
    fun delete(human: Human): Int

    @Query("SELECT * FROM human ORDER BY name ASC")
    fun getAll(): List<Human>

    @Query("SELECT * FROM human WHERE gender = :gender ORDER BY lastName ASC")
    fun getAllByGender(gender: Int): List<Human>

    @Query("SELECT * FROM human WHERE id = :id")
    fun getById(id: Long): Human
}