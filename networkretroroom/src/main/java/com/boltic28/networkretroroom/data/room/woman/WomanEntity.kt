package com.boltic28.networkretroroom.data.room.woman

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.networkretroroom.data.room.TypesConverter

@Entity(tableName = "woman")
@TypeConverters(TypesConverter::class)
data class WomanEntity(
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