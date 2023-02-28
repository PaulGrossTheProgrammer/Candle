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
    private val animationPeriod = 250L

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
        private var ballRadius = 0F
        private var ballColour = Color.YELLOW
        private var ballColours: IntArray? = null

        private var widthMessage = ""
        private var heightMessage = ""

        private val paint = Paint()


        private fun buildBallColourArray(): IntArray {
            var myList = mutableListOf<Int>()

            val colourBaseH = 25f
            val colourBaseS = 0.65f
            val colourBaseV = 0.5f

            // TODO: Vary S and B too
            val hVariance = 5.0f

            for (i in 1..10) {
                val newH = colourBaseH + (kotlin.random.Random.nextFloat() * 2 * hVariance) - hVariance
                Log.d("buildBallColourArray", "newH = $newH")

                val c = Color.HSVToColor(floatArrayOf(newH, colourBaseS, colourBaseV) )
                myList.add(c)
            }

            Log.d("buildBallColourArray", "Colour count = ${myList.size}")
            return myList.toIntArray()
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            Log.d("CandleView", "onMeasure: width = $measuredWidth, height = $measuredHeight")

            midX = measuredWidth/2F
            midY = measuredHeight/2F

            widthMessage = "$measuredWidth ${mainContext?.getString(R.string.pixels)}"
            heightMessage = "$measuredHeight ${mainContext?.getString(R.string.pixels)}"

            ballX = midX
            ballY = midY
            ballRadius = measuredWidth * 0.1F
            ballColours = buildBallColourArray()

            paint.isAntiAlias = false
            paint.style = Paint.Style.FILL
        }

        fun doAnimation() {
            ballColour = ballColours?.random() ?:Color.YELLOW

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
            paint.color = ballColour
            canvas?.drawCircle(ballX, ballY, ballRadius, paint)

            /*
            paint.style = Paint.Style.FILL
            paint.color = Color.CYAN
            paint.textSize = 75F
            canvas?.drawText(widthMessage, (midX * 0.7F), (midY * 0.2F), paint)

            // draw the rotated text
            canvas?.save()
            canvas?.rotate(90F)
            canvas?.drawText(heightMessage, (midY * 1.2F), -midX, paint)
            canvas?.restore()
             */
        }
    }
}

