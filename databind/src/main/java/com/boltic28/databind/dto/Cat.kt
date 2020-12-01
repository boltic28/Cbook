package com.boltic28.databind.dto

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.boltic28.databind.dto.interf.Pet

class Cat(
    override val weight: ObservableDouble,
    override val photoUrl: ObservableField<String>,
    override val id: Long,
    override val name: ObservableField<String>,
    override val age: ObservableInt,
    override val isIll: ObservableBoolean,
    override val isCov: ObservableBoolean
    ): Pet