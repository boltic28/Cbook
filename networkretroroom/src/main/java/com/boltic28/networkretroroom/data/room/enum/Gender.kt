package com.boltic28.networkretroroom.data.room.enum

enum class Gender(var value: String) {
    MAN("male"),
    WOMAN("female");

    companion object{
        private val map = values().associateBy(Gender::value)
        fun fromString(type: String) = map[type]
    }
}