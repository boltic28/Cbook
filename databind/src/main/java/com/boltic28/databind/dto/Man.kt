package com.boltic28.databind.dto

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import com.boltic28.databind.dto.interf.Pet
import com.boltic28.databind.dto.interf.PetOwner
import com.boltic28.databind.dto.interf.Profession
import com.boltic28.databind.dto.interf.Worker

data class Man(
    override val id: Long,
    override val photoUrl: ObservableField<String>,
    override val profession: Profession,
    val tall: ObservableFloat,
    override val pet: ObservableField<Pet>,
    override val isCov: ObservableBoolean,
    override val name: ObservableField<String>,
    override val age: ObservableInt,
    override val isIll: ObservableBoolean
) : Worker, PetOwner