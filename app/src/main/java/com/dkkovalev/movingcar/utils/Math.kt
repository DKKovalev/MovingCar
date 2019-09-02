package com.dkkovalev.movingcar.utils

import kotlin.math.atan2

fun calculateAngle(x1: Float, y1: Float, x2: Float, y2: Float): Float =
    Math.toDegrees(atan2(y2 - y1, x2 - x1).toDouble()).toFloat()