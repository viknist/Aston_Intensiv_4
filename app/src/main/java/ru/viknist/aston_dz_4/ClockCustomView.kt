package ru.viknist.aston_dz_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val FLOAT_ZERO = 0f
private const val HOUR_HAND_WIDTH_DEFAULT = 10f
private const val MINUTE_HAND_WIDTH_DEFAULT = 7f
private const val SECOND_HAND_WIDTH_DEFAULT = 5f

class ClockCustomView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var centerX = FLOAT_ZERO
    private var centerY = FLOAT_ZERO
    private var radius = FLOAT_ZERO
    private var hourHandLength = FLOAT_ZERO
    private var minuteHandLength = FLOAT_ZERO
    private var secondHandLength = FLOAT_ZERO
    var hourHandColor = Color.BLACK
    var minuteHandColor = Color.BLACK
    var secondHandColor = Color.RED
    var hourHandWidth = HOUR_HAND_WIDTH_DEFAULT
    var minuteHandWidth = MINUTE_HAND_WIDTH_DEFAULT
    var secondHandWidth = SECOND_HAND_WIDTH_DEFAULT

    init {
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 5f
            strokeCap = Paint.Cap.ROUND
        }

        context.withStyledAttributes(attrs, R.styleable.ClockCustomView) {
            hourHandColor = getColor(R.styleable.ClockCustomView_hourHandColor, hourHandColor)
            minuteHandColor = getColor(R.styleable.ClockCustomView_minuteHandColor, minuteHandColor)
            secondHandColor = getColor(R.styleable.ClockCustomView_secondHandColor, secondHandColor)

            hourHandWidth = getFloat(R.styleable.ClockCustomView_hourHandWidth, hourHandWidth)
            minuteHandWidth = getFloat(R.styleable.ClockCustomView_minuteHandWidth, minuteHandWidth)
            secondHandWidth = getFloat(R.styleable.ClockCustomView_secondHandWidth, secondHandWidth)
        }
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        centerX = width / 2f
        centerY = height / 2f
        radius = (min(width, height) / 2f) * 0.8f
        hourHandLength = radius * 0.5f
        minuteHandLength = radius * 0.7f
        secondHandLength = radius * 0.9f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawClockCircle(canvas)
        drawClockMarkings(canvas)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        val hourAngle = (hour + minute / 60f) * 30f * Math.PI / 180f
        drawClockHand(canvas, hourAngle, hourHandColor, hourHandWidth, hourHandLength)

        val minuteAngle = minute * 6f * Math.PI / 180f
        drawClockHand(canvas, minuteAngle, minuteHandColor, minuteHandWidth, minuteHandLength)

        val secondAngle = second * 6f * Math.PI / 180f
        drawClockHand(canvas, secondAngle, secondHandColor, secondHandWidth, secondHandLength)

        postInvalidateDelayed(1000)
    }

    private fun drawClockCircle(canvas: Canvas){
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, centerY, radius, paint)
        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, radius - 10f, paint)
    }

    private fun drawClockMarkings(canvas: Canvas){
        for (i in 1..12) {
            val angle = i * 30f * Math.PI / 180f
            val x = (centerX + (radius - 50f) * sin(angle)).toFloat()
            val y = (centerY - (radius - 50f) * cos(angle)).toFloat()
            canvas.drawCircle(x, y, 5f, paint)
        }
    }

    private fun drawClockHand(
        canvas: Canvas,
        angle: Double,
        color: Int,
        width: Float,
        length: Float)
    {
        paint.color = color
        paint.strokeWidth = width
        canvas.drawLine(
            centerX,
            centerY,
            (centerX + length * sin(angle)).toFloat(),
            (centerY - length * cos(angle)).toFloat(),
            paint
        )
    }
}