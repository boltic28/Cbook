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

    @Query("SELECT * FROM human ORDER BY age ASC")
    fun getAllByAge(): List<Human>

    @Query("SELECT * FROM human ORDER BY lastName ASC")
    fun getAllByLastName(): List<Human>

    @Query("SELECT * FROM human WHERE type = :sex ORDER BY lastName ASC")
    fun getAllBySex(sex: Int): List<Human>

    @Query("SELECT * FROM human WHERE id = :id")
    fun getById(id: Long): Human
}