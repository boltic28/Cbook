package com.boltic28.networkretroroom.data.room.woman

import androidx.room.*
import io.reactivex.Single

@Dao
interface WomanDao {

    @Insert
    fun insert(womanEntity: WomanEntity): Single<Long>

    @Update
    fun update(womanEntity: WomanEntity): Single<Int>

    @Delete
    fun delete(womanEntity: WomanEntity): Single<Int>

    @Query("DELETE FROM woman")
    fun deleteAll(): Single<Int>

    @Query("SELECT * FROM woman ORDER BY name ASC")
    fun getAll(): Single<List<WomanEntity>>

    @Query("SELECT * FROM woman WHERE id = :id")
    fun getById(id: Long): Single<WomanEntity>
}