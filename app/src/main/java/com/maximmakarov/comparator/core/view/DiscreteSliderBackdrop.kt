package com.maximmakarov.comparator.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.maximmakarov.comparator.core.ext.dpToPx

class DiscreteSliderBackdrop @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val fillPaint = Paint()
    private val strokePaint = Paint()
    private var tickMarkCount = 0
    private var tickMarkRadius = 0.0f
    private var horizontalBarThickness = 0.0f
    private var backdropFillColor = 0
    private var backdropStrokeColor = 0
    private var backdropStrokeWidth = 0.0f
    private val xRadius = context.dpToPx(8) // The x-radius of the oval used to round the corners
    private val yRadius = context.dpToPx(8) // The y-radius of the oval used to round the corners
    private val discreteSliderBackdropLeftMargin = context.dpToPx(32)
    private val discreteSliderBackdropRightMargin = context.dpToPx(32)
    private var backdropBarRect: RectF? = null
    private var backdropJoinRect: RectF? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) updateRect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        val interval = (width - (discreteSliderBackdropLeftMargin + discreteSliderBackdropRightMargin)) / (tickMarkCount - 1)

        setUpFillPaint()
        setUpStrokePaint()

        canvas.drawRoundRect(backdropBarRect!!, xRadius.toFloat(), yRadius.toFloat(), fillPaint)
        canvas.drawRoundRect(backdropBarRect!!, xRadius.toFloat(), yRadius.toFloat(), strokePaint)

        for (i in 0 until tickMarkCount) {
            canvas.drawCircle((discreteSliderBackdropLeftMargin + i * interval).toFloat(), (height / 2).toFloat(), tickMarkRadius, fillPaint)
            canvas.drawCircle((discreteSliderBackdropLeftMargin + i * interval).toFloat(), (height / 2).toFloat(), tickMarkRadius, strokePaint)
        }

        canvas.drawRoundRect(backdropJoinRect!!, xRadius.toFloat(), yRadius.toFloat(), fillPaint)
    }

    private fun updateRect() {
        val width = width
        val height = height
        backdropBarRect = RectF(discreteSliderBackdropLeftMargin.toFloat(),
                height / 2 - horizontalBarThickness / 2,
                (width - discreteSliderBackdropRightMargin).toFloat(),
                height / 2 + horizontalBarThickness / 2)
        backdropJoinRect = RectF(discreteSliderBackdropLeftMargin.toFloat(),
                height / 2 - (horizontalBarThickness / 2 - context.dpToPx(1)),
                (width - discreteSliderBackdropRightMargin).toFloat(),
                height / 2 + (horizontalBarThickness / 2 - context.dpToPx(1)))
    }

    private fun setUpFillPaint() {
        fillPaint.color = backdropFillColor
        fillPaint.style = Paint.Style.FILL
        fillPaint.isAntiAlias = true
    }

    private fun setUpStrokePaint() {
        strokePaint.color = backdropStrokeColor
        strokePaint.style = Paint.Style.STROKE
        strokePaint.isAntiAlias = true
        strokePaint.strokeWidth = backdropStrokeWidth
    }

    fun setTickMarkCount(tickMarkCount: Int) {
        this.tickMarkCount = if (tickMarkCount < 2) 2 else tickMarkCount
    }

    fun setTickMarkRadius(tickMarkRadius: Float) {
        this.tickMarkRadius = if (tickMarkRadius < 2.0f) 2.0f else tickMarkRadius
    }

    fun setHorizontalBarThickness(horizontalBarThickness: Float) {
        this.horizontalBarThickness = if (horizontalBarThickness < 2.0f) 2.0f else horizontalBarThickness
        updateRect()
    }

    fun setBackdropFillColor(backdropFillColor: Int) {
        this.backdropFillColor = backdropFillColor
    }

    fun setBackdropStrokeColor(backdropStrokeColor: Int) {
        this.backdropStrokeColor = backdropStrokeColor
    }

    fun setBackdropStrokeWidth(backdropStrokeWidth: Float) {
        this.backdropStrokeWidth = if (backdropStrokeWidth < 1.0f) 1.0f else backdropStrokeWidth
    }
}
