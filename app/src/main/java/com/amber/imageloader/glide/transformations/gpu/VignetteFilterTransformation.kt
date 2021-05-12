package com.amber.imageloader.glide.transformations.gpu

import android.content.Context
import android.graphics.PointF
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter
import java.util.*

/**
 * @author lsy
 * @date 2021/05/10
 * @description
 */
class VignetteFilterTransformation @JvmOverloads constructor(
    private val mContext: Context,
    mPool: BitmapPool = Glide.get(mContext).bitmapPool,
    private val mCenter: PointF = PointF(0.5f, 0.5f),
    private val mColor: FloatArray = floatArrayOf(0.0f, 0.0f, 0.0f),
    private val mStart: Float = 0.0f,
    private val mEnd: Float = 0.75f
) : GPUFilterTransformation<GPUImageVignetteFilter>(mContext, mPool, GPUImageVignetteFilter()) {

    constructor(
        context: Context,
        center: PointF,
        color: FloatArray,
        start: Float,
        end: Float
    ) : this(
        context, Glide.get(context).bitmapPool, center, color, start, end
    )

    init {
        getFilter()?.let {
            it.setVignetteCenter(mCenter)
            it.setVignetteColor(mColor)
            it.setVignetteStart(mStart)
            it.setVignetteEnd(mEnd)
        }
    }

    override fun getId(): String {
        return "VignetteFilterTransformation(center=" + mCenter.toString() +
                ",color=" + mColor.contentToString() +
                ",start=" + mStart + ",end=" + mEnd + ")"
    }
}