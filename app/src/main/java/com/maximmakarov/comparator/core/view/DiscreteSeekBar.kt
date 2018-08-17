package com.maximmakarov.comparator.core.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar


class DiscreteSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private var stepSize = 0.0f
    private var startTrackingProgress = 0

    private var fromUserCount = 0 // If this counter exceeds 1 then the user dragged the SeekBar otherwise clicked
    private var onDiscreteSeekBarChangeListener: OnDiscreteSeekBarChangeListener? = null

    companion object {
        private const val PROGRESS_PROPERTY = "progress"
        private const val MULTIPLIER = 100
    }

    interface OnDiscreteSeekBarChangeListener {
        fun onPositionChanged(position: Int)
    }

    init {
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                startTrackingProgress = seekBar.progress
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) fromUserCount += 1
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val stopTrackingProgress = seekBar.progress
                val newProgress = countNewProgress(stopTrackingProgress)

                doAnimation(seekBar, if (fromUserCount > 1) stopTrackingProgress else startTrackingProgress, newProgress)

                fromUserCount = 0
                onDiscreteSeekBarChangeListener?.onPositionChanged(newProgress / MULTIPLIER)
            }
        })
    }

    private fun countNewProgress(oldProgress: Int) =
            if (oldProgress % stepSize >= stepSize / 2f)
                ((oldProgress / stepSize.toInt() + 1) * stepSize).toInt()
            else (oldProgress / stepSize.toInt() * stepSize).toInt()

    private fun doAnimation(seekBar: SeekBar, oldProgress: Int, newProgress: Int) {
        val animation = ObjectAnimator.ofInt(seekBar, PROGRESS_PROPERTY, oldProgress, newProgress)
        animation.duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

    fun setPosition(position: Int) {
        this.progress = position * stepSize.toInt()
    }

    fun setPositionAnimated(position: Int) {
        startTrackingProgress = progress
        val oldProgress = position * stepSize.toInt()
        val newProgress = countNewProgress(oldProgress)
        doAnimation(this, startTrackingProgress, newProgress)
        this.progress = newProgress
    }

    fun setTickMarkCount(tickMarkCount: Int) {
        val tickMarkCount1 = if (tickMarkCount < 2) 2 else tickMarkCount
        max = (tickMarkCount1 - 1) * MULTIPLIER
        this.stepSize = (max / (tickMarkCount1 - 1)).toFloat()
    }

    fun setOnDiscreteSeekBarChangeListener(onDiscreteSeekBarChangeListener: OnDiscreteSeekBarChangeListener) {
        this.onDiscreteSeekBarChangeListener = onDiscreteSeekBarChangeListener
    }
}
