package com.boltic28.networkretroroom.data.room.man

import androidx.room.*
import io.reactivex.Single

@Dao
interface ManDao {

    @Insert
    fun insert(manEntity: ManEntity): Single<Long>

    @Update
    fun update(manEntity: ManEntity): Single<Int>

    @Delete
    fun delete(manEntity: ManEntity): Single<Int>

    @Query("DELETE FROM man")
    fun deleteAll(): Single<Int>

    @Query("SELECT * FROM man ORDER BY name ASC")
    fun getAll(): Single<List<ManEntity>>

    @Query("SELECT * FROM man WHERE id = :id")
    fun getById(id: Long): Single<ManEntity>
}