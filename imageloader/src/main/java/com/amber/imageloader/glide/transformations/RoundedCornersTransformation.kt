package com.amber.imageloader.glide.transformations

import android.content.Context
import android.graphics.*
import com.amber.imageloader.annotation.CornerType
import com.amber.imageloader.constant.LoadConstant
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
class RoundedCornersTransformation @JvmOverloads constructor(
    pool: BitmapPool?, private val mRadius: Float, margin: Int,
    @CornerType cornerType: Int = LoadConstant.CORNER_ALL
) : BitmapTransformation(pool) {
    private val mDiameter: Float
    private val mMargin: Int

    @CornerType
    private val mCornerType: Int

    @JvmOverloads
    constructor(
        context: Context?, radius: Float, margin: Int,
        @CornerType cornerType: Int = LoadConstant.CORNER_ALL
    ) : this(Glide.get(context).bitmapPool, radius, margin, cornerType) {
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        var bitmap = pool[width, height, Bitmap.Config.ARGB_8888]
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(
            toTransform,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        drawRoundRect(canvas, paint, width.toFloat(), height.toFloat())
        return bitmap
    }

    private fun drawRoundRect(canvas: Canvas, paint: Paint, width: Float, height: Float) {
        val right = width - mMargin
        val bottom = height - mMargin
        when (mCornerType) {
            LoadConstant.CORNER_ALL -> canvas.drawRoundRect(
                RectF(
                    mMargin.toFloat(),
                    mMargin.toFloat(),
                    right,
                    bottom
                ), mRadius, mRadius, paint
            )
            LoadConstant.CORNER_TOP_LEFT -> drawTopLeftRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_TOP_RIGHT -> drawTopRightRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_BOTTOM_LEFT -> drawBottomLeftRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_BOTTOM_RIGHT -> drawBottomRightRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_TOP -> drawTopRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_BOTTOM -> drawBottomRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_LEFT -> drawLeftRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_RIGHT -> drawRightRoundRect(canvas, paint, right, bottom)
            LoadConstant.CORNER_OTHER_TOP_LEFT -> drawOtherTopLeftRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_OTHER_TOP_RIGHT -> drawOtherTopRightRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_OTHER_BOTTOM_LEFT -> drawOtherBottomLeftRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_OTHER_BOTTOM_RIGHT -> drawOtherBottomRightRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_DIAGONAL_FROM_TOP_LEFT -> drawDiagonalFromTopLeftRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            LoadConstant.CORNER_DIAGONAL_FROM_TOP_RIGHT -> drawDiagonalFromTopRightRoundRect(
                canvas,
                paint,
                right,
                bottom
            )
            else -> canvas.drawRoundRect(
                RectF(mMargin.toFloat(), mMargin.toFloat(), right, bottom),
                mRadius,
                mRadius,
                paint
            )
        }
    }

    private fun drawTopLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), mMargin + mDiameter, mMargin + mDiameter),
            mRadius, mRadius, paint
        )
        canvas.drawRect(
            RectF(mMargin.toFloat(), mMargin + mRadius, mMargin + mRadius, bottom),
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin.toFloat(), right, bottom), paint)
    }

    private fun drawTopRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin.toFloat(), right, mMargin + mDiameter), mRadius,
            mRadius, paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, mMargin + mRadius, right, bottom), paint)
    }

    private fun drawBottomLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), bottom - mDiameter, mMargin + mDiameter, bottom),
            mRadius, mRadius, paint
        )
        canvas.drawRect(
            RectF(
                mMargin.toFloat(),
                mMargin.toFloat(),
                mMargin + mDiameter,
                bottom - mRadius
            ), paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin.toFloat(), right, bottom), paint)
    }

    private fun drawBottomRightRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
            mRadius, paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
        canvas.drawRect(RectF(right - mRadius, mMargin.toFloat(), right, bottom - mRadius), paint)
    }

    private fun drawTopRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), right, mMargin + mDiameter),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin + mRadius, right, bottom), paint)
    }

    private fun drawBottomRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right, bottom - mRadius), paint)
    }

    private fun drawLeftRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), mMargin + mDiameter, bottom),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin.toFloat(), right, bottom), paint)
    }

    private fun drawRightRoundRect(canvas: Canvas, paint: Paint, right: Float, bottom: Float) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin.toFloat(), right - mRadius, bottom), paint)
    }

    private fun drawOtherTopLeftRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(
            RectF(
                mMargin.toFloat(),
                mMargin.toFloat(),
                right - mRadius,
                bottom - mRadius
            ), paint
        )
    }

    private fun drawOtherTopRightRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), mMargin + mDiameter, bottom),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), bottom - mDiameter, right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin.toFloat(), right, bottom - mRadius), paint)
    }

    private fun drawOtherBottomLeftRoundRect(
        canvas: Canvas,
        paint: Paint,
        right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), right, mMargin + mDiameter),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin.toFloat(), right, bottom), mRadius, mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin.toFloat(), mMargin + mRadius, right - mRadius, bottom), paint)
    }

    private fun drawOtherBottomRightRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), right, mMargin + mDiameter),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), mMargin + mDiameter, bottom),
            mRadius,
            mRadius,
            paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint)
    }

    private fun drawDiagonalFromTopLeftRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), mMargin.toFloat(), mMargin + mDiameter, mMargin + mDiameter),
            mRadius, mRadius, paint
        )
        canvas.drawRoundRect(
            RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
            mRadius, paint
        )
        canvas.drawRect(
            RectF(mMargin.toFloat(), mMargin + mRadius, right - mDiameter, bottom),
            paint
        )
        canvas.drawRect(
            RectF(mMargin + mDiameter, mMargin.toFloat(), right, bottom - mRadius),
            paint
        )
    }

    private fun drawDiagonalFromTopRightRoundRect(
        canvas: Canvas, paint: Paint, right: Float,
        bottom: Float
    ) {
        canvas.drawRoundRect(
            RectF(right - mDiameter, mMargin.toFloat(), right, mMargin + mDiameter), mRadius,
            mRadius, paint
        )
        canvas.drawRoundRect(
            RectF(mMargin.toFloat(), bottom - mDiameter, mMargin + mDiameter, bottom),
            mRadius, mRadius, paint
        )
        canvas.drawRect(
            RectF(
                mMargin.toFloat(),
                mMargin.toFloat(),
                right - mRadius,
                bottom - mRadius
            ), paint
        )
        canvas.drawRect(RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint)
    }

    override fun getId(): String {
        return ("RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ", diameter="
                + mDiameter + ", cornerType=" + mCornerType + ")")
    }

    init {
        mDiameter = mRadius * 2
        mMargin = margin
        mCornerType = cornerType
    }
}