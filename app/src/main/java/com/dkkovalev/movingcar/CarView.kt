package com.dkkovalev.movingcar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

private const val DX = 100f
private const val DY = 75f

class CarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : View(context, attrs, defAttrStyle) {

    private var paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GREEN
        strokeWidth = 10f
    }

    private var circlePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLUE
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        pivotX = DX
        pivotY = DY

        canvas?.drawRect(0f, 0f, 200f, 150f, paint)
        canvas?.drawCircle(DX, DY, 20f, circlePaint)
        invalidate()
    }

    override fun setX(x: Float) {
        super.setX(x - DX)
    }

    override fun setY(y: Float) {
        super.setY(y - DY)
    }

    override fun getX(): Float {
        return super.getX() + DX
    }

    override fun getY(): Float {
        return super.getY() + DY
    }
}