package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageFilter

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
open class GPUFilterTransformation<T : GPUImageFilter?>(
    private val mContext: Context?, private val mBitmapPool: BitmapPool?, private val mFilter: T?
) : BitmapTransformation(mBitmapPool) {

    constructor(context: Context, filter: T?) : this(
        context,
        Glide.get(context).bitmapPool,
        filter
    )


    override fun transform(
        pool: BitmapPool?,
        toTransform: Bitmap?,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        if (pool == null || toTransform == null) {
            return null
        }
        val gpuImage = GPUImage(mContext)
        gpuImage.setImage(toTransform)
        gpuImage.setFilter(mFilter)
        return gpuImage.bitmapWithFilterApplied
    }

    override fun getId(): String {
        return javaClass.simpleName
    }

    fun getFilter(): T? {
        return mFilter
    }
}