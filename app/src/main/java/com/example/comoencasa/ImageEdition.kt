package com.example.comoencasa
import android.graphics.*
import com.squareup.picasso.Transformation

class ImageEdition(private val radius: Int, private val margin: Int, private val borderColor: Int, private val borderWidth: Int) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val rect = RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat())
        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        if (borderWidth > 0) {
            val borderPaint = Paint()
            borderPaint.isAntiAlias = true
            borderPaint.color = borderColor
            borderPaint.style = Paint.Style.STROKE
            borderPaint.strokeWidth = borderWidth.toFloat()
            canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), borderPaint)
        }

        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "rounded(radius=$radius, margin=$margin, borderColor=$borderColor, borderWidth=$borderWidth)"
    }
}