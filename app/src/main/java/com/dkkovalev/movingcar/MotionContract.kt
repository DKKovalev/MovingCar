package com.dkkovalev.movingcar

interface MotionContract {
    interface View {
        fun onAngleCalculated(newX: Float, newY: Float, angle: Float, bearing: Float)
    }

    interface Presenter {
        fun moveCar(oldX: Float, oldY: Float, newX: Float, newY: Float)
    }
}
