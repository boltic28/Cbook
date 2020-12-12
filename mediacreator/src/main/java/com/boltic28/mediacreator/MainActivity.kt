package com.boltic28.mediacreator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ff_tag"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun pickFromGallery(intentCode: Int) {
        setupPermissions {
            val intent = Intent()
            intent.setTypeAndNormalize("video/*")
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), intentCode)
        }
    }

    fun initFF() {
        val ff = FFmpeg.getInstance(this)

        ff.loadBinary(object : FFmpegLoadBinaryResponseHandler {
            override fun onStart() {
                Log.d(TAG, "ff_onStart")
            }

            override fun onFinish() {
                Log.d(TAG, "ff_onFinish")
            }

            override fun onFailure() {
                Log.d(TAG, "ff_onFailure")
            }

            override fun onSuccess() {
                Log.d(TAG, "ff_onSuccess")
                val command: Array<String> = arrayOf("","")  //trimVideo("path", )
                try {
                    ff.execute(command, object : FFmpegExecuteResponseHandler {
                        override fun onStart() {
                            Log.d(TAG, "ff_success_onStart")
                        }

                        override fun onFinish() {
                            Log.d(TAG, "ff_success_onFinish")
                        }

                        override fun onSuccess(message: String?) {
                            Log.d(TAG, "ff_success_onSuccess")
                        }

                        override fun onProgress(message: String?) {
                            Log.d(TAG, "ff_success_onProgress")
                        }

                        override fun onFailure(message: String?) {
                            Log.d(TAG, "ff_success_onFail")
                        }
                    })
                } catch (e: FFmpegCommandAlreadyRunningException) {
                    Log.d(TAG, "ff_exception")
                }

            }

        })
    }

    /**
     * Start position "%d:%02d:%02d" format
     * End position "%02d:%02d" format
     **/
    fun trimVideo(inputPath: String, start: Long, end: Long, outPath: String): Array<String> {
        return arrayOf("-y", "-i", inputPath, "-ss", start.toMediaTime(), "-to", end.toMediaTime(), "-c", "copy", outPath)
    }

    lateinit var doThis: () -> Unit
    private fun setupPermissions(doSomething: () -> Unit) {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        doThis = doSomething
        if (writePermission != PackageManager.PERMISSION_GRANTED && readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 101)
        } else doThis()
    }


}



fun Long.toMediaTime(): String{
        val timeInSec = this/1000
        val sec = timeInSec % 60
        val min = (timeInSec / 60) % 60
        val hrs = timeInSec/ 3600
        return  if(hrs > 0){
            Formatter().format("%d:%02d:%02d", hrs, min, sec).toString()
        }else{
            Formatter().format("%02d:%02d", min, sec).toString()
        }
}