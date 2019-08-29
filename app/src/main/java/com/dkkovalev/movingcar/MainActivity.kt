package com.dkkovalev.movingcar

import android.os.Bundle
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
    }

    override fun onAngleCalculated(newX: Float, newY: Float, angle: Float, bearing: Float) {
        with(v_car) {
            //Движение по дуге
            rotate(
                bearing = bearing,
                interpolator = interpolator
            ).mergeWith(
                moveByCurve(
                    x = newX,
                    y = newY,
                    angle = angle,
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
    }
}
