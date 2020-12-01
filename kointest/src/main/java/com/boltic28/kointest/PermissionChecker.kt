package com.boltic28.kointest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionChecker(private val context: Context) {

    companion object {
        const val TAG = "test_mt"
        const val PERMISSION_REQUEST_CODE = 7777
    }

    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun requestForPermission(){}

    fun checkPermission(activity: Activity, permission: String) {
        val permissionResult = ContextCompat.checkSelfPermission(context, permission)

        if (permissionResult != PackageManager.PERMISSION_GRANTED) {
            val permissionArray =
                arrayOf(permission)
            ActivityCompat.requestPermissions(activity, permissionArray, PERMISSION_REQUEST_CODE)
        }
    }

    fun checkPermissions(activity: AppCompatActivity, permission: List<String>) {
        val resMap = permission.map {
            it to ContextCompat.checkSelfPermission(
                context, it
            )
        }.toMap()

        val permissionListForAsking = mutableListOf<String>()
        resMap.forEach { (permission, isGranted) ->
            if (isGranted != PackageManager.PERMISSION_GRANTED) {
                permissionListForAsking.add(permission)
            }
        }

        ActivityCompat.requestPermissions(
            activity,
            permissionListForAsking.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }
}

sealed class Permission(id:String){
    class Storage: Permission(id = Manifest.permission.WRITE_EXTERNAL_STORAGE)
}



var isServiceStarted = true

private fun startAppService() {

}