package com.boltic28.networkretroroom.`class`

import com.boltic28.networkretroroom.room.human.HumanEntity
import java.io.Serializable

interface Human {
    fun convertToEntity(): HumanEntity
}