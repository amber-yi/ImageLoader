package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter

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
/**
 * Kuwahara all the colors in the image.
 *
 * The radius to sample from when creating the brush-stroke effect, with a default of 25.
 * The larger the radius, the slower the filter.
 */
class KuwaharaFilterTransformation @JvmOverloads constructor(
    context: Context?,
    pool: BitmapPool? = Glide.get(context).bitmapPool,
    private val mRadius: Int = 25
) : GPUFilterTransformation<GPUImageKuwaharaFilter?>(context, pool, GPUImageKuwaharaFilter()) {
    constructor(context: Context?, radius: Int) : this(
        context,
        Glide.get(context).bitmapPool,
        radius
    )

    override fun getId(): String {
        return "KuwaharaFilterTransformation(radius=$mRadius)"
    }

    init {
        getFilter()?.setRadius(mRadius)
    }
}