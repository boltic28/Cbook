package com.boltic28.networkretroroom.data

import com.boltic28.networkretroroom.room.enum.Gender
import java.time.LocalDate

data class Woman (
    var id: Long,
    var gender: Gender,
    var title: String,
    var name: String,
    var lastName: String,
    var age: Int,
    var date: LocalDate,
    var photo: String,
    var phone: String,
    var mail: String
)