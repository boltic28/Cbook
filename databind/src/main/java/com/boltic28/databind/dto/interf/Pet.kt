package com.boltic28.databind.dto.interf

import androidx.databinding.ObservableDouble
import com.boltic28.databind.dto.interf.BaseItem

interface Pet: BaseItem {
    val weight: ObservableDouble
}