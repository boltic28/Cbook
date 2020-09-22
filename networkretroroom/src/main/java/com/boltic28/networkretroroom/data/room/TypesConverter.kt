package com.boltic28.networkretroroom.data.room

import androidx.room.TypeConverter
import com.boltic28.networkretroroom.data.room.enum.Gender
import java.time.LocalDate

class TypesConverter {

    @TypeConverter
    fun genderFromString(gender: String): Gender = Gender.fromString(gender) ?: Gender.MAN

    @TypeConverter
    fun genderToString(gender: Gender): String = gender.value

    @TypeConverter
    fun dateFromTimestamp(value: Long): LocalDate = LocalDate.ofEpochDay(value)

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): Long = date.toEpochDay()
}