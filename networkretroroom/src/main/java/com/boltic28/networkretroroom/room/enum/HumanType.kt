package com.boltic28.networkretroroom.room.enum

enum class HumanType(var value: String) {
    MAN("male"),
    WOMAN("female");

    companion object{
        private val map = values().associateBy(HumanType::value)
        fun fromString(type: String) = map[type]
    }
}