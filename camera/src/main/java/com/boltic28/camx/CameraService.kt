package com.boltic28.camx

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.util.Log

class CameraService(manager: CameraManager, cameraId: String) {

    private lateinit var mCameraID: String
    private lateinit var cameraDevice: CameraDevice
    private lateinit var session: CameraCaptureSession
    private lateinit var previewBuilder: CaptureRequest.Builder

    private val callback = object: CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Log.d("","")
        }
    }
}