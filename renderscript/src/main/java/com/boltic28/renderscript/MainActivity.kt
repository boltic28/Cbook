package com.boltic28.renderscript

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "wtf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blur_view.setImageResource(R.drawable.blur)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {
                blur_view.setImageResource(R.drawable.blur)
                if (seekBar.progress == 0) {
                    Log.d(TAG, "default picture")
                } else {
                    Log.d(TAG, "blurring rad: ${seekBar.progress}")
                    blur_view.setImageBitmap(
                        BlurUtil().blur(
                            this@MainActivity,
                            resources.getDrawable(R.drawable.blur).toBitmap(),
                            seekBar.progress.toFloat()
                        )
                    )
                }
            }
        })
    }
}
