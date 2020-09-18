package com.boltic28.networkretroroom.room.human

import androidx.room.*
import io.reactivex.Single

@Dao
interface HumanDao {

    @Insert
    fun insert(human: HumanEntity): Single<Long>

    @Update
    fun update(human: HumanEntity): Single<Int>

    @Delete
    fun delete(human: HumanEntity): Single<Int>

    @Query("SELECT * FROM human ORDER BY name ASC")
    fun getAll(): Single<List<HumanEntity>>

    @Query("SELECT * FROM human WHERE type = :gender ORDER BY lastName ASC")
    fun getAllBySex(gender: Int): Single<List<HumanEntity>>

    @Query("SELECT * FROM human WHERE id = :id")
    fun getById(id: Long): Single<HumanEntity>
}