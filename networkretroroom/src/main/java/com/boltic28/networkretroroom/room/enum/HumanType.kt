package com.boltic28.networkretroroom.room.enum

enum class HumanType(var value: Int) {
    MAN(1),
    WOMAN(0);

    companion object{
        private val map = values().associateBy(HumanType::value)
        fun fromInt(type: Int) = map[type]
    }
}