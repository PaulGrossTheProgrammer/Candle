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

    var candleView: CandleView? = null
    var myTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        candleView = CandleView(this, null)
        setContentView(candleView)
    }

    override fun onResume() {
        super.onResume()

        // Resume the animation
        myTimer = Timer()
        val delay = 0L
        val period = 25L

        var doThis = object : TimerTask() {
            override fun run() {
                // Log.v("TIMER", "running...")
                candleView?.doAnimation()
                candleView?.postInvalidate()
            }
        }

        myTimer?.scheduleAtFixedRate(doThis, delay, period)
    }

    override fun onPause() {
        super.onPause()

        Log.v("Candle", "onPause() running")
        myTimer?.cancel()
        Log.v("Candle", "Animation timer cancelled")
    }

    class CandleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

        private var ballX = 200F

        fun doAnimation() {
            //Log.v("CandleView", "doAnimation()")
            ballX += 2
            if (ballX > 500) {
                ballX = 200F
            }
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            //Log.v("CandleView", "onDraw()")

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

