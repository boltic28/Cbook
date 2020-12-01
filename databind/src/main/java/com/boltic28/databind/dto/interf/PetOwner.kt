package com.boltic28.databind.dto.interf

import androidx.databinding.ObservableField

interface PetOwner: Worker {
    val pet: ObservableField<Pet>
}