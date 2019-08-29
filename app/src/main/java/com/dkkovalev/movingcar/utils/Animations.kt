package com.dkkovalev.movingcar.utils

import android.animation.ObjectAnimator
import android.graphics.Path
import android.view.View
import android.view.animation.Interpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import kotlin.math.cos
import kotlin.math.sin

fun View.rotate(
    duration: Long = 1000,
    bearing:Float,
    interpolator: Interpolator
): Completable {
    val animationSubject = CompletableSubject.create()
    return animationSubject.doOnSubscribe {
        ViewCompat.animate(this)
            .setInterpolator(interpolator)
            .setDuration(duration)
            .rotation(bearing)
            .withEndAction { animationSubject.onComplete() }
    }
}

fun View.move(duration: Long = 1000, x: Float, y: Float, interpolator: Interpolator): Completable {
    val animationSubject = CompletableSubject.create()
    return animationSubject.doOnSubscribe {
        ViewCompat.animate(this)
            .setInterpolator(interpolator)
            .setDuration(duration)
            .translationX(x)
            .translationY(y)
            .withEndAction { animationSubject.onComplete() }
    }
}

fun View.moveByCurve(
    duration: Long = 1000,
    x: Float,
    y: Float,
    angle: Float,
    interpolator: Interpolator
): Completable {

    val path = Path()
    path.reset()
    path.moveTo(getX(), getY())
    path.cubicTo(
        getX() * sin(angle),
        getY() * cos(angle),
        getX() * sin(angle),
        getY() * cos(angle),
        x,
        y
    )

    val animationSubject = CompletableSubject.create()
    return animationSubject.doOnSubscribe {
        ObjectAnimator.ofFloat(this, View.X, View.Y, path).apply {
            this.interpolator = interpolator
            this.duration = duration
            doOnEnd { animationSubject.onComplete() }
            start()
        }
    }
}
