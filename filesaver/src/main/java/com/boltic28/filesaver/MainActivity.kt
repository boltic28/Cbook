package com.boltic28.filesaver

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val permissionCode = 7777

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
            ActivityCompat.requestPermissions(this, permissionArray, permissionCode)
        }
    }

    private fun writeFileToExternalStorage(string: String) {
        testDirectory(Directories.EXTERNAL)
        val file = File(Directories.FILE_EXT.value)
        file.createNewFile()
        file.writeText(string)
        showMessage("File wrote in EXTERNAL storage")
    }

    private fun readFromExternalStorage() {
        try {
            val file = File(Directories.FILE_EXT.value)
            val text = file.readText()
            text_for_writing.setText(text)
        } catch (e: FileNotFoundException) {
            showMessage("File not found")
        }
    }

    private fun clearExternalStorage() {
        testDirectory(Directories.EXTERNAL)
        val file = File(Directories.FILE_EXT.value)
        if (file.exists()) {
            file.delete()
            showMessage("EXTERNAL storage file deleted")
        } else {
            showMessage("File not found")
        }
    }

    private fun writeFileToInternalStorage(string: String) {
        val fos = openFileOutput("text.txt", Context.MODE_PRIVATE)
        try {
            fos.write(string.toByteArray())
            showMessage("File wrote in INTERNAL storage")
        } catch (e: IOException) {
            showMessage("File did not write")
        }
    }

    private fun readFromInternalStorage() {
        try {
            val fis = openFileInput("text.txt")
            val bytes = ByteArray(fis.available())
            fis.read(bytes)
            val text = String(bytes)
            text_for_writing.setText(text)
            showMessage("File read from INTERNAL storage")
        } catch (e: FileNotFoundException) {
            showMessage("File not found")
        }
    }

    private fun clearInternalStorage() {
        try {
            this.deleteFile("text.txt")
            showMessage("INTERNAL storage file deleted")
        } catch (e: FileNotFoundException) {
            showMessage("File not found")
        }
    }

    private fun testDirectory(path: Directories) {
        val file = File(path.value)
        if (!file.exists())
            file.mkdirs()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
