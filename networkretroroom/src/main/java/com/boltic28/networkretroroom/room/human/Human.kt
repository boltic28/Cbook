package com.boltic28.networkretroroom.room.human

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boltic28.networkretroroom.room.enum.HumanType
import java.time.LocalDate

@Entity(tableName = "human")
data class Human (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
//    @TypeConverters(HumanTypeConverter::class)
    val type: HumanType = HumanType.MAN,

    val title: String = "ms",
    val name: String = "nobody",
    val lastName: String = "who",

    val age: Int = 0,

//    @TypeConverters(DateConverter::class)
    val date: LocalDate = LocalDate.now(),

    val photo: String = "",
    val phone: String = "123456",
    val mail: String = "mail@mail.com"
)