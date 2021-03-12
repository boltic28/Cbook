package com.boltic28.filesaver

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.boltic28.filesaver.Directories.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files

class MainActivity : AppCompatActivity() {

    companion object {
        private const val WRITE_EXT_STORAGE_PERMISSION_REQUEST_CODE = 7777
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
        setButtons()
    }

    private fun setButtons() {
        create_ext_file.setOnClickListener {
            writeFileToExternalStorage(text_for_writing.text.toString())
        }

        create_int_file.setOnClickListener {
            writeFileToInternalStorage(text_for_writing.text.toString())
        }

        delete_ext_file.setOnClickListener {
            clearExternalStorage()
        }

        delete_int_file.setOnClickListener {
            clearInternalStorage()
        }

        read_from_ext.setOnClickListener {
            readFromExternalStorage()
        }

        read_from_int.setOnClickListener {
            readFromInternalStorage()
        }
    }

    private fun checkPermissions() {
        val checkPermissionResultStorageWrite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val checkPermissionResultStorageRead = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (checkPermissionResultStorageWrite != PackageManager.PERMISSION_GRANTED &&
            checkPermissionResultStorageRead != PackageManager.PERMISSION_GRANTED
        ) {
            val permissionArray =
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ActivityCompat.requestPermissions(
                this,
                permissionArray,
                WRITE_EXT_STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun writeFileToExternalStorage(string: String) {
        testExternalDirectory()
        try {
            val file = File(getExternalFilesDir(EXTERNAL_PATH.value), FILE_EXT.value)
            file.createNewFile()
            file.writeText(string)
            showMessage(resources.getString(R.string.file_wrote_ext))
        } catch (e: FileNotFoundException) {
            println("ERROR: $e")
            showMessage(resources.getString(R.string.file_not_found))
        }
    }

    private fun readFromExternalStorage() {
        try {
            val file = File(getExternalFilesDir(EXTERNAL_PATH.value), FILE_EXT.value)
            val text = file.readText()
            text_for_writing.setText(text)
            showMessage(resources.getString(R.string.file_read_int))
        } catch (e: FileNotFoundException) {
            showMessage(resources.getString(R.string.file_not_found))
        }
    }

    private fun clearExternalStorage() {
        testExternalDirectory()
        try {
            val file = File(getExternalFilesDir(EXTERNAL_PATH.value), FILE_EXT.value)
            file.delete()
            showMessage(resources.getString(R.string.file_deleted_ext))
        } catch (e: FileNotFoundException) {
            showMessage(resources.getString(R.string.file_not_found))
        }
    }

    private fun writeFileToInternalStorage(string: String) {
        val fos = openFileOutput(FILE_INT.value, Context.MODE_PRIVATE)
        try {
            fos.write(string.toByteArray())
            showMessage(resources.getString(R.string.file_wrote_int))
        } catch (e: IOException) {
            showMessage(resources.getString(R.string.file_not_written))
        }
    }

    private fun readFromInternalStorage() {
        try {
            val fis = openFileInput(FILE_INT.value)
            val bytes = ByteArray(fis.available())
            fis.read(bytes)
            val text = String(bytes)
            text_for_writing.setText(text)
            showMessage(resources.getString(R.string.file_read_int))
        } catch (e: FileNotFoundException) {
            showMessage(resources.getString(R.string.file_not_found))
        }
    }

    private fun clearInternalStorage() {
        try {
            this.deleteFile(FILE_INT.value)
            showMessage(resources.getString(R.string.file_deleted_int))
        } catch (e: FileNotFoundException) {
            showMessage(resources.getString(R.string.file_not_found))
        }
    }

    private fun testExternalDirectory() {
        val file = File(FILE_EXT.value)
        if (!file.exists())
            file.mkdirs()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

//how to get path with uri and mediaId
//val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, video.mediaId)
//var path = ""
//
//var cursor: Cursor? = null
//val column = "_data"
//val project = arrayOf("_data")
//try {
//    cursor = requireContext().contentResolver.query(uri, project, null, null,
//        null)
//    if (cursor != null && cursor.moveToFirst()) {
//        path = cursor.getString(cursor.getColumnIndexOrThrow(column))
//    }
//} finally {
//    cursor?.close()
//}