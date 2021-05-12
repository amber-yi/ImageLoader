package com.amber.imageloader.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorInt;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
public class CropCircleTransformation extends BitmapTransformation {

    private final float borderSize;
    private final int borderColor;

    public CropCircleTransformation(Context context) {
        this(Glide.get(context).getBitmapPool(), 0, Color.TRANSPARENT);
    }

    public CropCircleTransformation(Context context, float borderSize, @ColorInt int borderColor) {
        this(Glide.get(context).getBitmapPool(), borderSize, borderColor);
    }

    public CropCircleTransformation(BitmapPool pool, float borderSize, @ColorInt int borderColor) {
        super(pool);
        this.borderSize = borderSize;
        this.borderColor = borderColor;
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap square = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (circle == null) {
            circle = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (Float.compare(borderSize, 0f) == 1) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            canvas.drawCircle(
                    r,
                    r,
                    r - borderSize / 2f,
                    paint
            );

        }
        return circle;
    }

    @Override
    public String getId() {
        return "CropCircleTransformation2(borderSize=" + borderSize + ", borderColor=" + borderColor + ")";
    }
}
