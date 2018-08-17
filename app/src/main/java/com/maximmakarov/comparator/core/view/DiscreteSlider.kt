package com.maximmakarov.comparator.core.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.ext.dpToPx
import kotlinx.android.synthetic.main.discrete_slider.view.*

class DiscreteSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private var tickMarkCount: Int = 0
    private var tickMarkRadius: Float = 0.toFloat()
    private var position: Int = 0
    private var horizontalBarThickness: Float = 0.toFloat()
    private var backdropFillColor: Int = 0
    private var backdropStrokeColor: Int = 0
    private var backdropStrokeWidth: Float = 0.toFloat()
    private var thumb: Drawable? = null
    private var progressDrawable: Drawable? = null
    private var onDiscreteSliderChangeListener: OnDiscreteSliderChangeListener? = null
    private val discreteSeekBarLeftPadding = context.dpToPx(32)
    private val discreteSeekBarRightPadding = context.dpToPx(32)

    interface OnDiscreteSliderChangeListener {
        fun onPositionChanged(position: Int)
    }

    init {
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.DiscreteSlider)

        try {
            tickMarkCount = attributeArray.getInteger(R.styleable.DiscreteSlider_tickMarkCount, 5)
            tickMarkRadius = attributeArray.getDimension(R.styleable.DiscreteSlider_tickMarkRadius, 8f)
            position = attributeArray.getInteger(R.styleable.DiscreteSlider_position, 0)
            horizontalBarThickness = attributeArray.getDimension(R.styleable.DiscreteSlider_horizontalBarThickness, 4f)
            backdropFillColor = attributeArray.getColor(R.styleable.DiscreteSlider_backdropFillColor, Color.GRAY)
            backdropStrokeColor = attributeArray.getColor(R.styleable.DiscreteSlider_backdropStrokeColor, Color.GRAY)
            backdropStrokeWidth = attributeArray.getDimension(R.styleable.DiscreteSlider_backdropStrokeWidth, 1f)
            thumb = attributeArray.getDrawable(R.styleable.DiscreteSlider_thumb)
            progressDrawable = attributeArray.getDrawable(R.styleable.DiscreteSlider_progressDrawable)
        } finally {
            attributeArray.recycle()
        }
        View.inflate(context, R.layout.discrete_slider, this)

        setTickMarkCount(tickMarkCount)
        setTickMarkRadius(tickMarkRadius)
        setHorizontalBarThickness(horizontalBarThickness)
        setBackdropFillColor(backdropFillColor)
        setBackdropStrokeColor(backdropStrokeColor)
        setBackdropStrokeWidth(backdropStrokeWidth)
        setPosition(position)
        setThumb(thumb)
        setProgressDrawable(progressDrawable)

        discreteSeekBar!!.setPadding(discreteSeekBarLeftPadding, 0, discreteSeekBarRightPadding, 0)

        discreteSeekBar!!.setOnDiscreteSeekBarChangeListener(object : DiscreteSeekBar.OnDiscreteSeekBarChangeListener {
            override fun onPositionChanged(position: Int) {
                if (onDiscreteSliderChangeListener != null) {
                    setPosition(position)
                    onDiscreteSliderChangeListener!!.onPositionChanged(position)
                }
            }
        })
    }

    fun setTickMarkCount(tickMarkCount: Int) {
        this.tickMarkCount = tickMarkCount
        discreteSliderBackdrop!!.setTickMarkCount(tickMarkCount)
        discreteSliderBackdrop!!.invalidate()
        discreteSeekBar!!.setTickMarkCount(tickMarkCount)
        discreteSeekBar!!.invalidate()
    }

    fun setTickMarkRadius(tickMarkRadius: Float) {
        this.tickMarkRadius = tickMarkRadius
        discreteSliderBackdrop!!.setTickMarkRadius(tickMarkRadius)
        discreteSliderBackdrop!!.invalidate()
    }

    fun setPosition(position: Int, animated: Boolean = true) {
        when {
            position < 0 -> this.position = 0
            position > tickMarkCount - 1 -> this.position = tickMarkCount - 1
            else -> this.position = position
        }
        if(animated) discreteSeekBar!!.setPositionAnimated(this.position)
    }

    fun setHorizontalBarThickness(horizontalBarThickness: Float) {
        discreteSliderBackdrop!!.setHorizontalBarThickness(horizontalBarThickness)
        discreteSliderBackdrop!!.invalidate()
    }

    fun setBackdropFillColor(backdropFillColor: Int) {
        discreteSliderBackdrop!!.setBackdropFillColor(backdropFillColor)
        discreteSliderBackdrop!!.invalidate()
    }

    fun setBackdropStrokeColor(backdropStrokeColor: Int) {
        discreteSliderBackdrop!!.setBackdropStrokeColor(backdropStrokeColor)
        discreteSliderBackdrop!!.invalidate()
    }

    fun setBackdropStrokeWidth(backdropStrokeWidth: Float) {
        discreteSliderBackdrop!!.setBackdropStrokeWidth(backdropStrokeWidth)
        discreteSliderBackdrop!!.invalidate()
    }

    fun setThumb(thumb: Drawable?) {
        if (thumb != null) {
            discreteSeekBar!!.thumb = thumb
            discreteSeekBar!!.invalidate()
        }
    }

    fun setProgressDrawable(progressDrawable: Drawable?) {
        if (progressDrawable != null) {
            discreteSeekBar!!.progressDrawable = progressDrawable
            discreteSeekBar!!.invalidate()
        }
    }

    fun setOnDiscreteSliderChangeListener(onDiscreteSliderChangeListener: OnDiscreteSliderChangeListener) {
        this.onDiscreteSliderChangeListener = onDiscreteSliderChangeListener
    }

    fun getTickMarkCount(): Int {
        return tickMarkCount
    }

    fun getTickMarkRadius(): Float {
        return tickMarkRadius
    }

    fun getPosition(): Int {
        return position
    }
}
