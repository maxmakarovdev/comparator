package com.maximmakarov.comparator.core.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.ColorRes
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

fun Context.startCircularRevealAnimation(view: View, args: AnimationArgs, @ColorRes startColor: Int, @ColorRes endColor: Int, onAnimEnd: () -> Unit) {
    view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            v.removeOnLayoutChangeListener(this)
            val duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            val finalRadius = Math.sqrt((args.width * args.width + args.height * args.height).toDouble()).toFloat()
            startCircularAnimation(view, args.centerX, args.centerY, 0f, finalRadius, duration, onAnimEnd)
            startColorAnimation(view, getColorCompat(startColor), getColorCompat(endColor), duration)
        }
    })
}

fun Context.startCircularExitAnimation(view: View, args: AnimationArgs, @ColorRes startColor: Int, @ColorRes endColor: Int, onAnimEnd: () -> Unit) {
    val duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
    val initRadius = Math.sqrt((args.width * args.width + args.height * args.height).toDouble()).toFloat()
    startCircularAnimation(view, args.centerX, args.centerY, initRadius, 0f, duration, onAnimEnd)
    startColorAnimation(view, startColor, endColor, duration)
}

private fun startCircularAnimation(view: View, x: Int, y: Int, startRadius: Float, endRadius: Float, duration: Long, onAnimEnd: () -> Unit) {
    val anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius)
    anim.duration = duration
    anim.interpolator = FastOutSlowInInterpolator()
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) = onAnimEnd()
    })
    anim.start()
}

private fun startColorAnimation(view: View, startColor: Int, endColor: Int, duration: Long) {
    val anim = ValueAnimator()
    anim.setIntValues(startColor, endColor)
    anim.setEvaluator(ArgbEvaluator())
    anim.addUpdateListener { valueAnimator -> view.setBackgroundColor(valueAnimator.animatedValue as Int) }
    anim.duration = duration
    anim.start()
}


class AnimationArgs(val centerX: Int, val centerY: Int, val width: Int, val height: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(centerX)
        parcel.writeInt(centerY)
        parcel.writeInt(width)
        parcel.writeInt(height)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<AnimationArgs> {
        override fun createFromParcel(parcel: Parcel): AnimationArgs {
            return AnimationArgs(parcel)
        }

        override fun newArray(size: Int): Array<AnimationArgs?> {
            return arrayOfNulls(size)
        }
    }
}