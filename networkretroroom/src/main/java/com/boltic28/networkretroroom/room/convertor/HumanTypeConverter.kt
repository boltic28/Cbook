package com.boltic28.networkretroroom.room.convertor

import androidx.room.TypeConverter
import com.boltic28.networkretroroom.room.enum.HumanType

class HumanTypeConverter {

    @TypeConverter
    fun typeFromInt(type: String) = HumanType.fromString(type)

    @TypeConverter
    fun typeToInt(type: HumanType) = type.value
}