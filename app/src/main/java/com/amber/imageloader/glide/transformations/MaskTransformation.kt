package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.*
import com.amber.imageloader.glide.transformations.internal.Utils.getMaskDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Copyright (C) 2017 Wasabeef
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class MaskTransformation(
    private val mContext: Context,
    private val mBitmapPool: BitmapPool,
    private val mMaskId: Int
) : BitmapTransformation(mBitmapPool) {
    companion object {
        private val sMaskingPaint = Paint()

        init {
            sMaskingPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
    }

    /**
     * @param maskId If you change the mask file, please also rename the mask file, or Glide will get
     * the cache with the old mask. Because getId() return the same values if using the
     * same make file name. If you have a good idea please tell us, thanks.
     */
    constructor(context: Context, maskId: Int) : this(
        context,
        Glide.get(context).bitmapPool,
        maskId
    ) {
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        var result = mBitmapPool[width, height, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        val mask = getMaskDrawable(mContext, mMaskId)
        if (mask != null) {
            val canvas = Canvas(result)
            mask.setBounds(0, 0, width, height)
            mask.draw(canvas)
            canvas.drawBitmap(toTransform, 0f, 0f, sMaskingPaint)
        }
        return result
    }

    override fun getId(): String {
        return ("MaskTransformation(maskId=" + mContext.resources.getResourceEntryName(mMaskId)
                + ")")
    }
}