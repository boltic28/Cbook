package com.boltic28.networkretroroom.data.dto

import com.boltic28.networkretroroom.data.room.enum.Gender
import java.time.LocalDate

data class Human(
    val id: Long,
    val gender: Gender,
    val title: String,
    val name: String,
    val lastName: String,
    val age: Int,
    val date: LocalDate,
    val photo: String,
    val phone: String,
    val mail: String
)