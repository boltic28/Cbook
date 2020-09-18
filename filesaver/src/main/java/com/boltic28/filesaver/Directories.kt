package com.boltic28.filesaver

import android.annotation.SuppressLint

enum class Directories(val value: String) {

    @SuppressLint("SdCardPath")
    EXTERNAL_PATH("/temp/"),
    FILE_INT("text.txt"),
    FILE_EXT("text.txt")

}