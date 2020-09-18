package com.boltic28.networkretroroom.room.convertor

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun dateFromTimestamp(value: Long): LocalDate = LocalDate.ofEpochDay(value)

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): Long = date.toEpochDay()
}