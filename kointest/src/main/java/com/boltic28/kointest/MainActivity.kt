package com.boltic28.kointest

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.boltic28.kointest.repository.AppRepo
import com.boltic28.kointest.repository.user.UserRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.time.LocalTime


class MainActivity : AppCompatActivity() {
    val tag = "wtf"

    private val database: AppRepo by inject()

    private val sharedPreferences: SharedPreferences by inject()

    private val userRepository: UserRepository by inject()

    private val permissionChecker: PermissionChecker by inject()

    private lateinit var handlerActivity: Handler
    private val userInactivityAction = Runnable {
        setBrightness(1)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handlerActivity = Handler(this.mainLooper)

        if (!Settings.System.canWrite(this)) {
            Log.d(tag, "send intent")
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            Log.d(tag, "package:" + this.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityForResult(
                intent,
                111
            )
        } else {
            Log.d(tag, "permission granted")
        }

        layout.setOnTouchListener { _, _ ->
            setBrightness(150)
            handlerActivity.removeCallbacks(userInactivityAction)
            handlerActivity.postDelayed(userInactivityAction, 10000)
            Log.d(tag, "timer delayed")
            true
        }

        turn_on_bright.setOnClickListener {
            setBrightness(200)
        }

        turn_off_bright.setOnClickListener {
            setBrightness(1)
        }
    }

    private fun setBrightness(value: Int) {
        Log.d(tag, "brightness is $value")
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, value)
    }

}


