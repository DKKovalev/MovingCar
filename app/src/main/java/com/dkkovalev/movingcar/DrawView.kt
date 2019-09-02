package com.dkkovalev.movingcar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager

class DrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var x1: Float = 0.toFloat()
    private var x2: Float = 0.toFloat()
    private var y1: Float = 0.toFloat()
    private var y2: Float = 0.toFloat()

    private val screenSize = Point()

    private val paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 3f
    }
    private val path: Path = Path()

    fun setPath(x1: Float, x2: Float, y1: Float, y2: Float) {
        this.x1 = x1
        this.x2 = x2
        this.y1 = y1
        this.y2 = y2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.reset()
        path.moveTo(x2, y2)
        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        display.getSize(screenSize)
        path.cubicTo(
            screenSize.x / 2f,
            0f,
            screenSize.x / 2f,
            screenSize.y.toFloat(),
            x1,
            y1
        )
        canvas.drawPath(path, paint)
    }
}