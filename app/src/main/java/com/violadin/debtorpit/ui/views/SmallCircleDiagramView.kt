package com.violadin.debtorpit.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.violadin.debtorpit.R

class SmallCircleDiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circleAngle = 360f
    private val defaultStrokeWidth = 10f
    private val defaultStartAngle = 0f
    private val defaultSpaceBetweenArcs = 0f
    private val defaultArcProportion = 0.0f
    private val defaultText = ""

    private val paintPrimary = Paint()
    private val paintSecondary = Paint()
    private val paintText = Paint()
    private var arcStrokeWidth = defaultStrokeWidth
    private var startAngle = defaultStartAngle
    private var spaceBetweenArcs = defaultSpaceBetweenArcs
    private var arcProportion: Float = defaultArcProportion
    private var text = defaultText

    private val rectangle: RectF by lazy {
        RectF(
            arcStrokeWidth / 2,
            arcStrokeWidth / 2,
            width.toFloat() - (arcStrokeWidth / 2),
            height.toFloat() - (arcStrokeWidth / 2)
        )
    }

    private val textSize: Float by lazy {
        height / 2f
    }

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SmallCircleDiagramView, 0, 0)

        arcStrokeWidth = typedArray.getDimension(
            R.styleable.SmallCircleDiagramView_strokeWidth,
            defaultStrokeWidth
        )
        startAngle =
            typedArray.getFloat(R.styleable.SmallCircleDiagramView_startAngle, defaultStartAngle)
        spaceBetweenArcs = typedArray.getFloat(
            R.styleable.SmallCircleDiagramView_spaceBetweenArcs,
            defaultSpaceBetweenArcs
        )
        arcProportion = typedArray.getFloat(
            R.styleable.SmallCircleDiagramView_arcProportion,
            defaultArcProportion
        )

        with(paintPrimary) {
            color = typedArray.getColor(
                R.styleable.SmallCircleDiagramView_colorPrimary,
                ContextCompat.getColor(context, R.color.base_UI)
            )
            strokeWidth = arcStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        with(paintSecondary) {
            color = typedArray.getColor(
                R.styleable.SmallCircleDiagramView_colorSecondary,
                ContextCompat.getColor(context, R.color.base_UI_mute)
            )
            strokeWidth = arcStrokeWidth
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

        with(paintText) {
            color = typedArray.getColor(
                R.styleable.SmallCircleDiagramView_textColor,
                ContextCompat.getColor(context, R.color.gray_dark)
            )
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }

        text = typedArray.getString(R.styleable.SmallCircleDiagramView_text) ?: defaultText
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawText(canvas)
        drawSecondaryArc(canvas)
        drawPrimaryArc(canvas)
    }

    private fun drawPrimaryArc(canvas: Canvas) {
        canvas.drawArc(
            rectangle,
            startAngle,
            arcProportion * circleAngle,
            false,
            paintPrimary
        )
    }

    private fun drawSecondaryArc(canvas: Canvas) {
        canvas.drawArc(
            rectangle,
            arcProportion * circleAngle + startAngle + spaceBetweenArcs,
            (1 - arcProportion) * circleAngle - (spaceBetweenArcs * 2),
            false,
            paintSecondary
        )
    }

    private fun drawText(canvas: Canvas) {
        calculateTextSize()
        canvas.drawText(
            text,
            width / 2f,
            (height / 2f) - ((paintText.descent() + paintText.ascent()) / 2),
            paintText
        )
    }

    private fun calculateTextSize() {
        val bounds = Rect()
        paintText.getTextBounds(text, 0, text.length, bounds)
        val newTextSize = (textSize * (width - 4f * arcStrokeWidth)) / (bounds.width())
        if (newTextSize > textSize) {
            paintText.textSize = textSize
        } else {
            paintText.textSize = newTextSize
        }
        invalidate()
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun setArcProportion(arcProportion: Float) {
        this.arcProportion = arcProportion
        invalidate()
    }
}