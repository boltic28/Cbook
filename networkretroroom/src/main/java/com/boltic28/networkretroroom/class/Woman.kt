package com.boltic28.networkretroroom.`class`

import com.boltic28.networkretroroom.room.enum.HumanType
import com.boltic28.networkretroroom.room.human.HumanEntity
import java.io.Serializable
import java.time.LocalDate

class Woman (
    val id: Long = 0,
    val type: HumanType = HumanType.MAN,
    val title: String = "ms",
    val name: String = "nobody",
    val lastName: String = "who",
    val age: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val photo: String = "",
    val phone: String = "123456",
    val mail: String = "mail@mail.com"
): Human {
    override fun convertToEntity(): HumanEntity =
        HumanEntity(id, type, title, name, lastName, age, date, photo, phone, mail)
}