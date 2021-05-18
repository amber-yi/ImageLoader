package com.amber.imageloader.glide.transformations

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.renderscript.RSRuntimeException
import com.amber.imageloader.glide.transformations.internal.FastBlur.blur
import com.amber.imageloader.glide.transformations.internal.RSBlur.blur
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Copyright (C) 2017 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class BlurTransformation @JvmOverloads constructor(
    private val mContext: Context,
    pool: BitmapPool = Glide.get(mContext).bitmapPool,
    private var mRadius: Int = MAX_RADIUS,
    private var mSampling: Int = DEFAULT_DOWN_SAMPLING
) : BitmapTransformation(pool) {

    constructor(context: Context, radius: Int) : this(
        context,
        Glide.get(context).bitmapPool,
        radius,
        DEFAULT_DOWN_SAMPLING
    )

    constructor(context: Context, radius: Int, sampling: Int) : this(
        context,
        Glide.get(context).bitmapPool,
        radius,
        sampling
    )

    @SuppressLint("ObsoleteSdkInt")
    override fun transform(
        pool: BitmapPool?,
        toTransform: Bitmap?,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        if (pool == null || toTransform == null) {
            return null
        }
        val width = toTransform.width
        val height = toTransform.height
        val scaledWidth = width / mSampling
        val scaledHeight = height / mSampling
        var bitmap = pool[scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap!!)
        canvas.scale(1 / mSampling.toFloat(), 1 / mSampling.toFloat())
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                blur(mContext, bitmap, mRadius)
            } catch (e: RSRuntimeException) {
                blur(bitmap, mRadius, true)
            }
        } else {
            blur(bitmap, mRadius, true)
        }
        return bitmap
    }

    override fun getId(): String {
        return "BlurTransformation(radius=$mRadius, sampling=$mSampling)"
    }

    companion object {
        private const val MAX_RADIUS = 25
        private const val DEFAULT_DOWN_SAMPLING = 1
    }
}