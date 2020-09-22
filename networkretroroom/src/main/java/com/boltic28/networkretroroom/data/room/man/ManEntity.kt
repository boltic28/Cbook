package com.boltic28.networkretroroom.data.room.man

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "man")
data class ManEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val name: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val date: Long,
    val photo: String,
    val phone: String,
    val mail: String
)