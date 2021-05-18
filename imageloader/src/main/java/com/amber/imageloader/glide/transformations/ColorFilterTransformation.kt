package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.*
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
class ColorFilterTransformation(private val mBitmapPool: BitmapPool, private val mColor: Int) :
    BitmapTransformation(
        mBitmapPool
    ) {
    constructor(context: Context?, color: Int) : this(Glide.get(context).bitmapPool, color) {}

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val config = if (toTransform.config != null) toTransform.config else Bitmap.Config.ARGB_8888
        var bitmap = mBitmapPool[width, height, config]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config)
        }
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.colorFilter = PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        return bitmap
    }

    override fun getId(): String {
        return "ColorFilterTransformation(color=$mColor)"
    }
}