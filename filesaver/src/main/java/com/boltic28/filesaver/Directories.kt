package com.boltic28.filesaver

import android.annotation.SuppressLint

enum class Directories(val value: String) {

    @SuppressLint("SdCardPath")
    EXTERNAL("/sdcard/fls/temp/"),

    FILE_EXT(EXTERNAL.value + "text.txt")

}