package com.boltic28.mediacreator

import android.content.Context
import java.io.File

class ContentProvider(private val context: Context) {

    companion object {

        const val EXTERNAL_PATH = "/temp/"
        const val FILE_INT = "text.txt"
        const val FILE_EXT = "text.txt"
    }

    fun getVideo(): String {
        val file = File(context.getExternalFilesDir(EXTERNAL_PATH), FILE_EXT)
        return "path"
    }


}