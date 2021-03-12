package com.boltic28.renderscript

import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "wtf"
    private var disposable: Disposable = Disposables.disposed()
    private lateinit var blurMaker: BlurUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blurMaker = BlurUtil(this, ContextCompat.getDrawable(this, R.drawable.scene3)!!.toBitmap())

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p1 == 0) {
//                    blur_view.setImageResource(R.drawable.big_scene)
                    Log.d(tag, "default picture")
                } else {
                    blurMaker.blur(p1.toFloat())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {
//                p0?.let {bar ->
//                    if (bar.progress == 0) {
//                        blur_view.setImageResource(R.drawable.blur)
//                        Log.d(tag, "default picture")
//                    } else {
//                        blurMaker.blur(bar.progress.toFloat())
//                    }
//                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "init disposable")
        disposable = blurMaker.blur
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ bitmap ->
                Log.d(tag, "Blur is installed")
                blur_view.setImageBitmap(bitmap)
            }, {
                Log.d(tag, "Blur problem:\n $it")
            })
    }

    override fun onStop() {
        Log.d(tag, "close disposable")
        disposable.dispose()
        super.onStop()
    }
}
