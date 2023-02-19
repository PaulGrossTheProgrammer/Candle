package com.example.candle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(CandleView(this, null));
    }

    class CandleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            // custom drawing code here
            val paint = Paint()
            paint.style = Paint.Style.FILL

            // make the entire canvas white
            paint.color = Color.BLACK
            canvas!!.drawPaint(paint)

            // draw circle
            //paint.isAntiAlias = false
            paint.color = Color.WHITE
            canvas!!.drawCircle(200F, 200F, 150F, paint)


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