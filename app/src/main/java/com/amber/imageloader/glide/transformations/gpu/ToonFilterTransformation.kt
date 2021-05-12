package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter

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
 * The threshold at which to apply the edges, default of 0.2.
 * The levels of quantization for the posterization of colors within the scene,
 * with a default of 10.0.
 */
class ToonFilterTransformation @JvmOverloads constructor(
    context: Context?,
    pool: BitmapPool? = Glide.get(context).bitmapPool,
    private val mThreshold: Float = .2f,
    private val mQuantizationLevels: Float = 10.0f
) : GPUFilterTransformation<GPUImageToonFilter?>(context, pool, GPUImageToonFilter()) {
    constructor(context: Context?, threshold: Float, quantizationLevels: Float) : this(
        context,
        Glide.get(context).bitmapPool,
        threshold,
        quantizationLevels
    )

    override fun getId(): String {
        return "ToonFilterTransformation(threshold=" + mThreshold +
                ",quantizationLevels=" + mQuantizationLevels + ")"
    }

    init {
        getFilter()?.let {
            it.setThreshold(mThreshold)
            it.setQuantizationLevels(mQuantizationLevels)
        }
    }
}