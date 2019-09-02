package com.dkkovalev.movingcar

import com.dkkovalev.movingcar.utils.calculateAngle
import javax.inject.Inject

private const val ANGLE = .9f

class MotionPresenterImpl @Inject constructor(private val view: MotionContract.View) :
    MotionContract.Presenter {

    override fun moveCar(oldX: Float, oldY: Float, newX: Float, newY: Float) {
        view.onAngleCalculated(
            newX,
            newY,
            ANGLE,
            calculateAngle(oldX, oldY, newX, newY)
        )
    }
}