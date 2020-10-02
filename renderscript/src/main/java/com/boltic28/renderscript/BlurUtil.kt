package com.boltic28.renderscript

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import kotlin.math.roundToInt

class BlurUtil() {
    val TAG = "wtf"

    fun blur(context: Context, image: Bitmap, data: Float): Bitmap {
        Log.d(TAG, "radius -> $data")

        val scale = data/100
        val radius = data/4

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

        return outBitmap
    }
}