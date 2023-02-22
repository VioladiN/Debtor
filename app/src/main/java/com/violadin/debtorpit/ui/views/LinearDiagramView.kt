package com.violadin.debtorpit.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.violadin.debtorpit.R

class LinearDiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val maxColumnHeight = 200f.dpToFloat()
    private val columnWidth = 40f.dpToFloat()
    private val spaceBetweenColumns = 60f.dpToFloat()
    private val marginBottom = 40f.dpToFloat()
    private val borderRadius = 4f.dpToFloat()
    private val textValueSize = 18f.dpToFloat()
    private val textTitleSize = 14f.dpToFloat()

    private val paintPrimary = Paint()
    private val paintSecondary = Paint()
    private val paintText = Paint()
    private val paintTextValues = Paint()

    private var primaryValue = 0f
    private var secondaryValue = 0f

    private var secondaryLeft = 0f
    private var secondaryTop = 0f
    private var secondaryRight = 0f
    private var secondaryBottom = 0f

    private var primaryLeft = 0f
    private var primaryTop = 0f
    private var primaryRight = 0f
    private var primaryBottom = 0f

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.LinearDiagramView, 0, 0)

        with(paintPrimary) {
            color = typedArray.getColor(
                R.styleable.LinearDiagramView_colorPrimaryColumn,
                ContextCompat.getColor(context, R.color.base_UI)
            )
        }

        with(paintSecondary) {
            color = typedArray.getColor(
                R.styleable.LinearDiagramView_colorSecondaryColumn,
                ContextCompat.getColor(context, R.color.base_UI_mute)
            )
        }

        with(paintText) {
            color = Color.BLACK
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = textTitleSize
        }

        with(paintTextValues) {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = textValueSize
            setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        }

        primaryValue = typedArray.getFloat(R.styleable.LinearDiagramView_primaryCount, 0f)
        secondaryValue = typedArray.getFloat(R.styleable.LinearDiagramView_secondaryCount, 0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        calculateValues(primaryValue, secondaryValue)
        drawPrimaryColumn(canvas)
        drawSecondaryColumn(canvas)
        drawTitles(canvas)
        drawTextValues(canvas)
    }

    private fun drawPrimaryColumn(canvas: Canvas) {
        canvas.drawRoundRect(
            primaryLeft,
            primaryTop,
            primaryRight,
            primaryBottom,
            borderRadius,
            borderRadius,
            paintPrimary
        )
    }

    private fun drawSecondaryColumn(canvas: Canvas) {
        canvas.drawRoundRect(
            secondaryLeft,
            secondaryTop,
            secondaryRight,
            secondaryBottom,
            borderRadius,
            borderRadius,
            paintSecondary
        )
    }

    private fun drawTitles(canvas: Canvas) {
        canvas.drawText(
            resources.getString(R.string.my_debt),
            (width / 2) - (spaceBetweenColumns / 2) - (columnWidth / 2),
            height - (marginBottom / 2),
            paintText
        )
        canvas.drawText(
            resources.getString(R.string.debt_for_me_one_line),
            (width / 2) + (spaceBetweenColumns / 2) + (columnWidth / 2),
            height - (marginBottom / 2),
            paintText
        )
    }

    private fun drawTextValues(canvas: Canvas) {
        paintTextValues.color = paintSecondary.color
        canvas.drawText(
            secondaryValue.toString(),
            (width / 2) - (spaceBetweenColumns / 2) - (columnWidth / 2),
            secondaryTop - (marginBottom / 4),
            paintTextValues
        )
        paintTextValues.color = paintPrimary.color
        canvas.drawText(
            primaryValue.toString(),
            (width / 2) + (spaceBetweenColumns / 2) + (columnWidth / 2),
            primaryTop - (marginBottom / 4),
            paintTextValues
        )
    }

    private fun calculateValues(primary: Float, secondary: Float) {
        secondaryLeft = (width / 2) - (spaceBetweenColumns / 2 + columnWidth)
        secondaryRight = (width / 2) - (spaceBetweenColumns / 2)

        primaryLeft = (width / 2) + (spaceBetweenColumns / 2)
        primaryRight = (width / 2) + (spaceBetweenColumns / 2 + columnWidth)

        if (primary == 0f && secondary == 0f) {
            primaryTop = height - marginBottom
            primaryBottom = height - marginBottom

            secondaryTop = height - marginBottom
            secondaryBottom = height - marginBottom
            return
        }

        if (primary == secondary) {
            primaryTop = height - marginBottom - maxColumnHeight
            primaryBottom = height - marginBottom

            secondaryTop = height - marginBottom - maxColumnHeight
            secondaryBottom = height - marginBottom
            return
        }
        if (primary > secondary) {
            secondaryTop = height - marginBottom - (secondaryValue * maxColumnHeight / primaryValue)
            secondaryBottom = height - marginBottom

            primaryTop = height - marginBottom - maxColumnHeight
            primaryBottom = height - marginBottom
            return
        } else {
            secondaryTop = height - marginBottom - maxColumnHeight
            secondaryBottom = height - marginBottom

            primaryTop = height - marginBottom - (primaryValue * maxColumnHeight / secondaryValue)
            primaryBottom = height - marginBottom
            return
        }
    }

    fun setValues(primaryValue: Float, secondaryValue: Float) {
        this.primaryValue = primaryValue
        this.secondaryValue = secondaryValue
        invalidate()
    }

    fun Float.dpToFloat(): Float {
        val scale = resources.displayMetrics.density
        return (this * scale)
    }
}