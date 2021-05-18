package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
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
class CropTransformation @JvmOverloads constructor(
    pool: BitmapPool?,
    width: Int = 0,
    height: Int = 0,
    cropType: CropType = CropType.CENTER
) : BitmapTransformation(pool) {
    enum class CropType {
        TOP, CENTER, BOTTOM
    }

    private var mBitmapPool: BitmapPool? = null
    private var mWidth = 0
    private var mHeight = 0
    private var mCropType = CropType.CENTER

    constructor(context: Context?) : this(Glide.get(context).bitmapPool) {}
    constructor(context: Context?, width: Int, height: Int) : this(
        Glide.get(context).bitmapPool,
        width,
        height
    )

    constructor(context: Context?, width: Int, height: Int, cropType: CropType) : this(
        Glide.get(
            context
        ).bitmapPool, width, height, cropType
    )

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        mWidth = if (mWidth == 0) toTransform.width else mWidth
        mHeight = if (mHeight == 0) toTransform.height else mHeight
        val config = if (toTransform.config != null) toTransform.config else Bitmap.Config.ARGB_8888
        var bitmap = mBitmapPool!![mWidth, mHeight, config]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(mWidth, mHeight, config)
        }
        val scaleX = mWidth.toFloat() / toTransform.width
        val scaleY = mHeight.toFloat() / toTransform.height
        val scale = Math.max(scaleX, scaleY)
        val scaledWidth = scale * toTransform.width
        val scaledHeight = scale * toTransform.height
        val left = (mWidth - scaledWidth) / 2
        val top = getTop(scaledHeight)
        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(toTransform, null, targetRect, null)
        return bitmap
    }

    override fun getId(): String {
        return ("CropTransformation(width=" + mWidth + ", height=" + mHeight + ", cropType=" + mCropType
                + ")")
    }

    private fun getTop(scaledHeight: Float): Float {
        return when (mCropType) {
            CropType.TOP -> 0f
            CropType.CENTER -> (mHeight - scaledHeight) / 2
            CropType.BOTTOM -> mHeight - scaledHeight
            else -> 0f
        }
    }

    init {
        mBitmapPool = pool
        mWidth = width
        mHeight = height
        mCropType = cropType
    }
}