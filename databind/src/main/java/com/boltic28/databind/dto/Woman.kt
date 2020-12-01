package com.boltic28.databind.dto

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.boltic28.databind.dto.interf.Pet
import com.boltic28.databind.dto.interf.PetOwner
import com.boltic28.databind.dto.interf.Profession
import com.boltic28.databind.dto.interf.Worker

class Woman(
    override val id: Long,
    override val photoUrl: ObservableField<String>,
    override val profession: Profession,
    override val pet: ObservableField<Pet>,
    val isMother: ObservableBoolean,
    override val name: ObservableField<String>,
    override val age: ObservableInt,
    override val isIll: ObservableBoolean,
    override val isCov: ObservableBoolean
): Worker, PetOwner