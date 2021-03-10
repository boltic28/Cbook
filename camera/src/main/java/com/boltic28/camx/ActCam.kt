package com.boltic28.camx
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_cam.*
import java.io.File

class ActCam : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "camLG"
    }

    private lateinit var myCameras: Array<CameraService?>
    private lateinit var cameraManager: CameraManager
    private lateinit var mMediaRecorder: MediaRecorder
    private val CAMERA1 = 0
    private var count = 1
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: Handler? = null
    private var mCurrentFile: File? = null

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread!!.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)
    }

    private fun stopBackgroundThread() {
        mBackgroundThread!!.quitSafely()
        try {
            mBackgroundThread!!.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_cam)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // work


        myCameras[CAMERA1]?.openCamera()
        mMediaRecorder.start()
        myCameras[CAMERA1]?.stopRecordingVideo()


        try {
            myCameras = arrayOfNulls(cameraManager.cameraIdList.size)
            for (cameraID in cameraManager.cameraIdList) {
                Log.i(LOG_TAG, "cameraID: $cameraID")
                myCameras[cameraID.toInt()] = CameraService(cameraManager, cameraID)
            }
        } catch (e: CameraAccessException) {
            Log.e(LOG_TAG, e.message!!)
            e.printStackTrace()
        }
        setUpMediaRecorder()
    }

    private fun setUpMediaRecorder() {
        mMediaRecorder = MediaRecorder()
        with(mMediaRecorder) {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mCurrentFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "test$count.mp4"
            )
            setOutputFile(mCurrentFile!!.absolutePath)

            val profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P)

            setVideoFrameRate(profile.videoFrameRate)
            setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight)
            setVideoEncodingBitRate(profile.videoBitRate)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(profile.audioBitRate)
            setAudioSamplingRate(profile.audioSampleRate)
            try {
                prepare()
            } catch (e: Exception) {
                Log.i(LOG_TAG, "не запустили медиа рекордер")
            }
        }
    }

    inner class CameraService(private val cameraManager: CameraManager, private val cameraID: String) {

        private var mCameraDevice: CameraDevice? = null
        private lateinit var mSession: CameraCaptureSession
        private var mPreviewBuilder: CaptureRequest.Builder? = null

        private val mCameraCallback: CameraDevice.StateCallback =
            object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    mCameraDevice = camera
                    Log.i(LOG_TAG, "Open camera  with id:" + mCameraDevice?.id)
                    startCameraPreviewSession()
                }

                override fun onDisconnected(camera: CameraDevice) {
                    mCameraDevice?.close()
                    Log.i(LOG_TAG, "disconnect camera  with id:" + mCameraDevice?.id)
                    mCameraDevice = null
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    Log.i(LOG_TAG, "error! camera id:" + camera.id + " error:" + error)
                }
            }

        private fun startCameraPreviewSession() {
            val texture = video_view.surfaceTexture
            texture?.setDefaultBufferSize(480, 600)
            val surface = Surface(texture)
            try {
                mPreviewBuilder = mCameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
                mPreviewBuilder?.addTarget(surface)

                val recorderSurface = mMediaRecorder.surface
                mPreviewBuilder!!.addTarget(recorderSurface)
                mCameraDevice!!.createCaptureSession(
                    listOf(
                        surface,
                        mMediaRecorder.surface
                    ),
                    object : CameraCaptureSession.StateCallback() {
                        override fun onConfigured(session: CameraCaptureSession) {
                            mSession = session
                            try {
                                mSession.setRepeatingRequest(
                                    mPreviewBuilder!!.build(),
                                    null,
                                    mBackgroundHandler
                                )
                            } catch (e: CameraAccessException) {
                                e.printStackTrace()
                            }
                        }

                        override fun onConfigureFailed(session: CameraCaptureSession) {}
                    }, mBackgroundHandler
                )
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }

        fun stopRecordingVideo() {
            try {
                with(mSession) {
                    stopRepeating()
                    abortCaptures()
                    close()
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            mMediaRecorder.stop()
            mMediaRecorder.release()
            count++
            setUpMediaRecorder()
            startCameraPreviewSession()
        }

        val isOpen: Boolean
            get() = mCameraDevice != null

        fun openCamera() {
            try {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraManager.openCamera(this.cameraID, mCameraCallback, mBackgroundHandler)
                }
            } catch (e: CameraAccessException) {
                Log.i(LOG_TAG, e.message!!)
            }
        }
    }

    public override fun onPause() {
        stopBackgroundThread()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        startBackgroundThread()
    }
}