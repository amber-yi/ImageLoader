package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import com.amber.imageloader.glide.GlideLoadUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
class FitXY constructor(pool: BitmapPool) : BitmapTransformation(pool) {
    constructor(context: Context?) : this(Glide.get(context).bitmapPool)

    override fun transform(
        pool: BitmapPool?,
        toTransform: Bitmap?,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        if (pool == null || toTransform == null) {
            return null
        }
        if (toTransform.width == outWidth && toTransform.height == outHeight) {
            return toTransform
        }
        val matrix = Matrix()
        matrix.setScale(
            outWidth.toFloat() / toTransform.width.toFloat(),
            outHeight.toFloat() / toTransform.height.toFloat()
        )
        val result =
            pool[outWidth, outHeight, getSafeConfig(toTransform)]
        // We don't add or remove alpha, so keep the alpha setting of the Bitmap we were given.
        TransformationUtils.setAlpha(toTransform, result)
        GlideLoadUtils.applyMatrix(toTransform,result,matrix)
        return result
    }

    private fun getSafeConfig(bitmap: Bitmap): Bitmap.Config? {
        return if (bitmap.config != null) bitmap.config else Bitmap.Config.ARGB_8888
    }

    override fun getId(): String {
        return "FitXYTransformation.com.amber.imageloader.glide.transformations"
    }
}