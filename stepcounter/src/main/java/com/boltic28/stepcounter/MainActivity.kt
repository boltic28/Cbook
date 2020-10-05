package com.boltic28.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "wtf"
    private lateinit var manager: SensorManager
    private var sensor: Sensor? = null
    private var stepsAfterStart = 0

    private val listener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            Log.d(tag, "sensor - ${p0?.name}, data: - $p1")
        }

        override fun onSensorChanged(p0: SensorEvent?) {
            Log.d(
                tag,
                "sensor - x:${p0?.values?.get(0)} y:${p0?.values?.get(1)} z:${p0?.values?.get(2)}}"
            )
            if (p0?.values?.get(1)!! > 11f) {
                steps.text = resources.getString(R.string.steps, stepsAfterStart++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        steps.text = resources.getString(R.string.steps, stepsAfterStart)

        manager.getSensorList(Sensor.TYPE_ALL).forEach {
            Log.d(tag, "sensor: ${it.name}")
        }

        if (sensor == null) {
            Log.d(tag, "can not get the sensor")
        } else {
            Log.d(tag, "sensor is created")
            Log.d(tag, "data: ${(sensor as Sensor).name}")
        }
    }

    override fun onResume() {
        super.onResume()
        sensor?.let {
            manager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensor?.let {
            manager.unregisterListener(listener)
        }
    }
}
