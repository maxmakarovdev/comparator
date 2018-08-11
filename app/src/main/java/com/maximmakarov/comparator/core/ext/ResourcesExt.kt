package com.maximmakarov.comparator.core.ext

import android.content.Context
import android.util.DisplayMetrics
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun Context.dpToPx(dp: Int) = Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Context.getColorCompat(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int) = ContextCompat.getDrawable(this, drawableResId)
