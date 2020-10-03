package com.boltic28.renderscript

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlin.math.roundToInt

class BlurUtil(private val context: Context,private val image: Bitmap) {

    private val tag = "wtf"
    private var scale = 1f

    private val blurS: BehaviorSubject<Bitmap> = BehaviorSubject.createDefault<Bitmap>(image)
        val blur: Observable<Bitmap>
            get() = blurS.hide()

    fun blur(position: Float) {
        Log.d(tag, "position -> $position")

        scale = 1 - position/100
        val radius = 10.5f

        val width = (image.width * scale).roundToInt()
        val height = (image.height * scale).roundToInt()

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outBitmap = Bitmap.createBitmap(inputBitmap)

        val render = RenderScript.create(context)
        val elem = Element.U8_4(render)
        val blur = ScriptIntrinsicBlur.create(render, elem)

        val tmpIn = Allocation.createFromBitmap(render, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(render, outBitmap)

        blur.setInput(tmpIn)
        blur.forEach(tmpOut)
        blur.setRadius(radius)

        tmpOut.copyTo(outBitmap)
        tmpIn.destroy()
        tmpOut.destroy()

        Log.d(tag, "scale -> $scale")
        blurS.onNext(outBitmap)
    }
}