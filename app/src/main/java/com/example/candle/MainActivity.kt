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

        // Start the animation
        animationTimer = Timer()

        val animationTask = object : TimerTask() {
            override fun run() {
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

        private var mainContext: Context? = null  // A pointer to the main context for accessing resources.

        init {
            mainContext = context
        }

        private var midY = 0F
        private var midX = 0F

        private var ballX = 0F
        private var ballY = 0F

        private var widthMessage = ""
        private var heightMessage = ""

        private val paint = Paint()

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            Log.d("CandleView", "onMeasure: width = $measuredWidth, height = $measuredHeight")

            midX = measuredWidth/2F
            midY = measuredHeight/2F

            widthMessage = "$measuredWidth ${mainContext?.getString(R.string.pixels)}"
            heightMessage = "$measuredHeight ${mainContext?.getString(R.string.pixels)}"

            ballX = midX
            ballY = midY
        }

        fun doAnimation() {
            //Log.d("CandleView", "doAnimation()")
            ballX += 5
            if (ballX > measuredWidth) {
                ballX = 0F
            }

            ballY += 5
            if (ballY > measuredHeight) {
                ballY = 0F
            }

            // NOTE: Don't call invalidate() directly, use postInvalidate() instead,
            // because the caller might not be calling from  a UI thread.
            postInvalidate()  // Safely mark this View as needing onDraw() again.
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            //Log.d("CandleView", "onDraw()")

            // custom drawing code here
            paint.style = Paint.Style.FILL

            // make the entire canvas black
            paint.color = Color.BLACK
            canvas?.drawPaint(paint)

            // draw circle
            paint.isAntiAlias = false
            paint.color = Color.WHITE
            canvas?.drawCircle(ballX, ballY, 150F, paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.CYAN
            paint.textSize = 75F
            canvas?.drawText(widthMessage, (midX * 0.7F), (midY * 0.2F), paint)

            // draw the rotated text
            canvas?.save()
            canvas?.rotate(90F)
            canvas?.drawText(heightMessage, (midY * 1.3F), -midX, paint)
            canvas?.restore()
        }
    }
}

