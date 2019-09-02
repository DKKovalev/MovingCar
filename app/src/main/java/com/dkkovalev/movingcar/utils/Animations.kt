package com.dkkovalev.movingcar.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.view.animation.Interpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import com.dkkovalev.movingcar.CarView
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject

fun View.rotate(
    duration: Long = 1000,
    bearing: Float
): Completable {
    val animationSubject = CompletableSubject.create()
    return animationSubject.doOnSubscribe {
        ViewCompat.animate(this)
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

fun CarView.moveByCurve(
    duration: Long = 1000,
    x: Float,
    y: Float,
    interpolator: Interpolator
): Completable {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val size = Point()
    display.getSize(size)

    val path = Path()
    path.reset()
    path.moveTo(getX(), getY())
    path.cubicTo(
        size.x / 2f,
        0f,
        size.x / 2f,
        size.y.toFloat(),
        x,
        y
    )

    val animationSubject = CompletableSubject.create()
    return animationSubject.doOnSubscribe {
        ObjectAnimator.ofFloat(this, View.X, View.Y, path).apply {
            addUpdateListener {
                this@moveByCurve.rotation = calculateAngle(getX(), getY(), x, y)
            }
            this.interpolator = interpolator
            this.duration = duration
            doOnEnd { animationSubject.onComplete() }
            start()
        }
    }
}
