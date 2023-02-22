package com.violadin.debtorpit.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.violadin.debtorpit.R

class CircleDiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circleAngle = 360f
    private val arcStrokeWidth = 10f.dpToFloat()
    private val radiusSecondaryCircle = 100f.dpToFloat()
    private val radiusPrimaryCircle = 125f.dpToFloat()
    private val textDimen = 40f.dpToFloat()

    private val paintSecondary = Paint()
    private val paintPrimary = Paint()
    private val paintText = Paint()

    private var externalArcProportion = 0.0f
    private var internalArcProportion = 0.0f
    private val startAngle = 90f
    private var text = ""

    private val internalRectangle: RectF by lazy {
        RectF(
            (width / 2 ) - radiusSecondaryCircle,
            (height / 2) - radiusSecondaryCircle,
            (width / 2) + radiusSecondaryCircle,
            (height / 2) + radiusSecondaryCircle
        )
    }

    private val externalRectangle: RectF by lazy {
        RectF(
            (width / 2 ) - radiusPrimaryCircle,
            (height / 2) - radiusPrimaryCircle,
            (width / 2) + radiusPrimaryCircle,
            (height / 2) + radiusPrimaryCircle
        )
    }

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CircleDiagramView, 0, 0)

        with(paintSecondary) {
            color = typedArray.getColor(R.styleable.CircleDiagramView_colorSecondaryCircle,
            ContextCompat.getColor(context, R.color.base_UI_mute))
            strokeWidth = arcStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        with(paintPrimary) {
            color = typedArray.getColor(R.styleable.CircleDiagramView_colorPrimaryCircle,
                ContextCompat.getColor(context, R.color.base_UI))
            strokeWidth = arcStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        with(paintText) {
            color = typedArray.getColor(R.styleable.CircleDiagramView_colorPrimaryCircle,
                ContextCompat.getColor(context, R.color.base_UI))
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = textDimen
            setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        }

        text = typedArray.getString(R.styleable.CircleDiagramView_textPercent) ?: ""
        internalArcProportion = typedArray.getFloat(R.styleable.CircleDiagramView_internalArcProportion, 0f)
        externalArcProportion = typedArray.getFloat(R.styleable.CircleDiagramView_externalArcProportion, 0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircles(canvas)
        drawInternalArc(canvas)
        drawExternalArc(canvas)
        drawText(canvas)
    }

    private fun drawInternalArc(canvas: Canvas) {
        canvas.drawArc(
            internalRectangle,
            startAngle,
            internalArcProportion * circleAngle,
            false,
            paintPrimary
        )
    }

    private fun drawExternalArc(canvas: Canvas) {
        canvas.drawArc(
            externalRectangle,
            startAngle,
            externalArcProportion * circleAngle,
            false,
            paintPrimary
        )
    }

    private fun drawCircles(canvas: Canvas) {
        canvas.drawCircle(width / 2f, height / 2f, radiusPrimaryCircle, paintSecondary)
        canvas.drawCircle(width / 2f, height / 2f, radiusSecondaryCircle, paintSecondary)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText(text, width / 2f, height / 2f - ((paintText.descent() + paintText.ascent()) / 2), paintText)
    }

    fun Float.dpToFloat(): Float {
        val scale = resources.displayMetrics.density
        return (this * scale)
    }

    fun setExternalArcProportion(arcProportion: Float) {
        this.externalArcProportion = arcProportion
        invalidate()
    }

    fun setInternalArcProportion(arcProportion: Float) {
        this.internalArcProportion = arcProportion
        invalidate()
    }
}