package com.boltic28.kointest.repository.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun getUsers(): List<UserEntity>
}