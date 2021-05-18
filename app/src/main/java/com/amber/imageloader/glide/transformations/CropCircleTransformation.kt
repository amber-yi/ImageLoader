package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
class CropCircleTransformation(
    pool: BitmapPool?,
    private val borderSize: Float,
    @param:ColorInt private val borderColor: Int
) : BitmapTransformation(pool) {
    constructor(context: Context?) : this(Glide.get(context).bitmapPool, 0f, Color.TRANSPARENT) {}
    constructor(context: Context?, borderSize: Float, @ColorInt borderColor: Int) : this(
        Glide.get(
            context
        ).bitmapPool, borderSize, borderColor
    )

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return circleCrop(pool, toTransform)!!
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val square = Bitmap.createBitmap(source, x, y, size, size)
        var circle = pool[size, size, Bitmap.Config.ARGB_8888]
        if (circle == null) {
            circle = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(circle!!)
        val paint = Paint()
        paint.shader =
            BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        if (java.lang.Float.compare(borderSize, 0f) == 1) {
            paint.shader = null
            paint.color = borderColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderSize
            canvas.drawCircle(
                r,
                r,
                r - borderSize / 2f,
                paint
            )
        }
        return circle
    }

    override fun getId(): String {
        return "CropCircleTransformation2(borderSize=$borderSize, borderColor=$borderColor)"
    }
}