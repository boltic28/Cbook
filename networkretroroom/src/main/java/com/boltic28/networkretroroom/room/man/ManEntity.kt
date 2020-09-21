package com.boltic28.networkretroroom.room.man

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.boltic28.networkretroroom.room.TypesConverter
import com.boltic28.networkretroroom.room.enum.Gender
import java.time.LocalDate

@Entity(tableName = "man")
@TypeConverters(TypesConverter::class)
data class ManEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var name: String = "",
    var lastName: String = "",
    var age: Int = 0,
    var gender: Gender = Gender.MAN,
    var date: LocalDate = LocalDate.now(),
    var photo: String = "",
    var phone: String = "",
    var mail: String = ""
)

// i have to make () - constructor or default values for any field (request from of room)