package com.boltic28.cbook.data

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class Process(val name: String, val id: Long, val timer: Int): Parcelable {

    @IgnoredOnParcel
    var now: Int = 0
    @IgnoredOnParcel
    var left: Int = timer - now
}