package com.boltic28.networkretroroom.room.human

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.networkretroroom.room.convertor.DateConverter
import com.boltic28.networkretroroom.room.convertor.HumanTypeConverter
import com.boltic28.networkretroroom.room.enum.HumanType
import java.time.LocalDate

@Entity(tableName = "human")
data class Human (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var name: String,
    var lastName: String,
    var age: Int,
    var gender: String,
    var photo: String,
    var phone: String,
    var mail: String
)