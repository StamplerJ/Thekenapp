package com.jann_luellmann.thekenapp.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.jann_luellmann.thekenapp.R

object Util {
    fun convertDpToPixel(dp: Float, context: Context): Int {
        return Math.round(dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun convertPixelsToDp(px: Float, context: Context): Int {
        return Math.round(px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun Context.isTablet(): Boolean {
        return this.resources.getBoolean(R.bool.isTablet)
    }

    fun Context.getStatusBarHeight(): Int {
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
}