package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.Bitmap
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
class CropSquareTransformation(private val mBitmapPool: BitmapPool) : BitmapTransformation(
    mBitmapPool
) {
    private var mWidth = 0
    private var mHeight = 0

    constructor(context: Context?) : this(Glide.get(context).bitmapPool) {}

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val size = Math.min(toTransform.width, toTransform.height)
        mWidth = (toTransform.width - size) / 2
        mHeight = (toTransform.height - size) / 2
        val config = if (toTransform.config != null) toTransform.config else Bitmap.Config.ARGB_8888
        var bitmap = mBitmapPool[mWidth, mHeight, config]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(toTransform, mWidth, mHeight, size, size)
        }
        return bitmap
    }

    override fun getId(): String {
        return "CropSquareTransformation(width=$mWidth, height=$mHeight)"
    }
}