package com.example.candle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var candleView: CandleView? = null
    private var animationTimer: Timer? = null
    private val animationPeriod = 25L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        candleView = CandleView(this, null)
        setContentView(candleView)
    }

    override fun onResume() {
        super.onResume()

        // Resume the animation
        animationTimer = Timer()

        var animationTask = object : TimerTask() {
            override fun run() {
                // Log.d("animationTask", "running...")
                candleView?.doAnimation()
            }
        }

        animationTimer?.scheduleAtFixedRate(animationTask, 0L, animationPeriod)
    }

    override fun onPause() {
        super.onPause()

        Log.d("Candle", "onPause() running")
        animationTimer?.cancel()
        Log.d("Candle", "Animation timer cancelled")
    }

    class CandleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

        private var ballX = 200F

        fun doAnimation() {
            //Log.d("CandleView", "doAnimation()")
            ballX += 2
            if (ballX > 500) {
                ballX = 200F
            }

            // NOTE: Don't call invalidate() directly
            // because the caller might not be from a UI thread.
            postInvalidate()  // Safely mark this View as needing onDraw() again.
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            //Log.d("CandleView", "onDraw()")

            // custom drawing code here
            val paint = Paint()
            paint.style = Paint.Style.FILL

            // make the entire canvas white
            paint.color = Color.BLACK
            canvas!!.drawPaint(paint)

            // draw circle
            paint.isAntiAlias = false
            paint.color = Color.WHITE
            canvas!!.drawCircle(ballX, 200F, 150F, paint)


            // draw green circle with anti aliasing turned on
            paint.isAntiAlias = true
            paint.color = Color.GREEN
            canvas!!.drawCircle(660F, 1320F, 150F, paint)

            // draw red rectangle with anti aliasing turned off
            paint.isAntiAlias = false
            paint.color = Color.RED
            canvas!!.drawRect(100F, 500F, 200F, 730F, paint)

            // draw the rotated text
            canvas!!.save()
            canvas!!.rotate(-15F)
            //canvas!!.restore()

            paint.style = Paint.Style.FILL
            paint.color = Color.CYAN
            paint.setTextSize(75F)
            canvas!!.drawText("Graphics Rotation", 40F, 780F, paint)

            //undo the rotate
            canvas!!.restore()
        }
    }
}

