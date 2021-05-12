package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import android.graphics.PointF
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter

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
 * Creates a swirl distortion on the image.
 */
class SwirlFilterTransformation @JvmOverloads constructor(
    context: Context?,
    pool: BitmapPool? = Glide.get(context).bitmapPool,
    private val mRadius: Float = .5f,
    private val mAngle: Float = 1.0f,
    private val mCenter: PointF = PointF(0.5f, 0.5f)
) : GPUFilterTransformation<GPUImageSwirlFilter?>(context, pool, GPUImageSwirlFilter()) {
    constructor(context: Context?, radius: Float, angle: Float, center: PointF) : this(
        context,
        Glide.get(context).bitmapPool,
        radius,
        angle,
        center
    )

    override fun getId(): String {
        return "SwirlFilterTransformation(radius=" + mRadius +
                ",angle=" + mAngle + ",center=" + mCenter.toString() + ")"
    }

    /**
     * @param radius from 0.0 to 1.0, default 0.5
     * @param angle minimum 0.0, default 1.0
     * @param center default (0.5, 0.5)
     */
    init {
        getFilter()?.let {
            it.setRadius(mRadius)
            it.setAngle(mAngle)
            it.setCenter(mCenter)
        }
    }
}