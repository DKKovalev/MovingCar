package com.dkkovalev.movingcar

import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

private const val ANGLE = .9f

class MotionPresenterImpl @Inject constructor(private val view: MotionContract.View) :
    MotionContract.Presenter {

    override fun moveCar(oldX: Float, oldY: Float, newX: Float, newY: Float) {
        view.onAngleCalculated(
            newX,
            newY,
            ANGLE,
            getBearing(oldX, oldY, newX * sin(ANGLE), newY * cos(ANGLE))
        )
    }

    private fun getBearing(beginX: Float, beginY: Float, endX: Float, endY: Float): Float {
        val lat = abs(beginX - endX)
        val lng = abs(beginY - endY)

        return when {
            beginX < endX && beginY < endY -> Math.toDegrees(atan(lng / lat).toDouble()).toFloat()
            beginX >= endX && beginY < endY -> (90 - Math.toDegrees(atan(lng / lat).toDouble()) + 90).toFloat()
            beginX >= endX && beginY >= endY -> (Math.toDegrees(atan(lng / lat).toDouble()) + 180).toFloat()
            beginX < endX && beginY >= endY -> (90 - Math.toDegrees(atan(lng / lat).toDouble()) + 270).toFloat()
            else -> -1f
        }
    }
}