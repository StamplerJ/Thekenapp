package com.jann_luellmann.thekenapp.util

import android.content.Context
import android.util.DisplayMetrics

object Util {
    fun convertDpToPixel(dp: Float, context: Context): Int {
        return Math.round(dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun convertPixelsToDp(px: Float, context: Context): Int {
        return Math.round(px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
    }
}