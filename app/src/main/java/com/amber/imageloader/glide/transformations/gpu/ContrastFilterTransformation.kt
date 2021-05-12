package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter

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
 * contrast value ranges from 0.0 to 4.0, with 1.0 as the normal level
 */
class ContrastFilterTransformation @JvmOverloads constructor(
    context: Context?,
    pool: BitmapPool? = Glide.get(context).bitmapPool,
    private val mContrast: Float = 1.0f
) : GPUFilterTransformation<GPUImageContrastFilter?>(context, pool, GPUImageContrastFilter()) {
    constructor(context: Context?, contrast: Float) : this(
        context,
        Glide.get(context).bitmapPool,
        contrast
    ) {
    }

    override fun getId(): String {
        return "ContrastFilterTransformation(contrast=$mContrast)"
    }

    init {
        getFilter()?.setContrast(mContrast)
    }
}