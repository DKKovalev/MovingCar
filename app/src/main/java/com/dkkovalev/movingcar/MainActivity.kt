package com.dkkovalev.movingcar

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.dkkovalev.movingcar.di.DaggerMotionComponent
import com.dkkovalev.movingcar.di.MotionModule
import com.dkkovalev.movingcar.utils.moveByCurve
import com.dkkovalev.movingcar.utils.rotate
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MotionContract.View {

    @Inject
    lateinit var presenter: MotionContract.Presenter

    private val interpolator = AccelerateDecelerateInterpolator()
    private val metrics = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMotionComponent.builder()
            .appComponent(App.component)
            .motionModule(MotionModule(this))
            .build()
            .inject(this)

        fl_root.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> presenter.moveCar(
                    v_car.x,
                    v_car.y,
                    motionEvent.x,
                    motionEvent.y
                )
            }

            return@setOnTouchListener true
        }

        windowManager.defaultDisplay.getMetrics(metrics)
    }

    override fun onAngleCalculated(newX: Float, newY: Float, angle: Float, bearing: Float) {

        with(v_car) {
            //Движение по дуге
           rotate(
                duration = 300,
                bearing = bearing
            ).mergeWith(
                moveByCurve(
                    duration = 3000,
                    x = newX,
                    y = newY,
                    interpolator = interpolator
                )
            ).subscribe()

            /*
            //Движение "танком"
            rotate(
                bearing = bearing,
                interpolator = interpolator
            ).andThen(
                move(
                    x = newX,
                    y = newY,
                    interpolator = interpolator
                )
            ).subscribe()
            */
        }

        draw.setPath(newX, v_car.x, newY, v_car.y)
        draw.invalidate()
    }


}
