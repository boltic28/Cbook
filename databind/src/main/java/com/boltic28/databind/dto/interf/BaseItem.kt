package com.boltic28.databind.dto.interf

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import java.io.Serializable

interface BaseItem: Serializable {
    val id: Long
    val photoUrl: ObservableField<String>
    val name: ObservableField<String>
    val age: ObservableInt
    val isIll: ObservableBoolean
    val isCov: ObservableBoolean
}