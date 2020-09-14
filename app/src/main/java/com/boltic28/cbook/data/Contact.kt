package com.boltic28.cbook.data

import android.os.Parcel
import android.os.Parcelable

data class Contact(
    var id: Long = 0,
    var name: String = "contact",
    var number: String = "+375 44 1234567",
    var mail: String = "contact@mail.com",
    var remark: String = "nothing",
    var photo: String = ""

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(number)
        parcel.writeString(mail)
        parcel.writeString(remark)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}