package com.amber.imageloader.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amber.imageloader.LoadConstant;
import com.amber.imageloader.R;
import com.amber.imageloader.ZImageLoader;
import com.amber.imageloader.glide.transformations.ColorFilterTransformation;
import com.amber.imageloader.glide.transformations.CropCircleTransformation;
import com.amber.imageloader.glide.transformations.GrayscaleTransformation;
import com.amber.imageloader.glide.transformations.MaskTransformation;
import com.amber.imageloader.glide.transformations.gpu.VignetteFilterTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import com.amber.imageloader.glide.transformations.BlurTransformation;
import com.amber.imageloader.glide.transformations.CropSquareTransformation;
import com.amber.imageloader.glide.transformations.CropTransformation;
import com.amber.imageloader.glide.transformations.RoundedCornersTransformation;
import com.amber.imageloader.glide.transformations.gpu.BrightnessFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.ContrastFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.InvertFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.KuwaharaFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.PixelationFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.SepiaFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.SketchFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.SwirlFilterTransformation;
import com.amber.imageloader.glide.transformations.gpu.ToonFilterTransformation;

/**
 * Created by Wasabeef on 2015/01/11.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private List<Type> mDataSet;

    enum Type {
        Mask,
        NinePatchMask,
        CropTop,
        CropCenter,
        CropBottom,
        CropSquare,
        CropCircle,
        ColorFilter,
        Grayscale,
        RoundedCorners,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Invert,
        Pixel,
        Sketch,
        Swirl,
        Brightness,
        Kuawahara,
        Vignette
    }

    public MainAdapter(Context context, List<Type> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (mDataSet.get(position)) {
            case Mask: {
                int width = Utils.dip2px(mContext, 133.33f);
                int height = Utils.dip2px(mContext, 126.33f);
//                Glide.with(mContext)
//                        .load(R.drawable.gif_test)
//                        .override(width, height)
//                        .bitmapTransform(new CenterCrop(mContext),
//                                new MaskTransformation(mContext, R.drawable.mask_starfish))
//
                ZImageLoader.with(mContext)
                        .load(R.drawable.gif_test)
                        .asGif()
                        .grayscale()
                        .centerCrop(60)
                        .override(width, height)
                        .into(holder.image);
                break;
            }
            case NinePatchMask: {
                int width = Utils.dip2px(mContext, 150.0f);
                int height = Utils.dip2px(mContext, 100.0f);
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .override(width, height)
                        .bitmapTransform(new CenterCrop(mContext),
                                new MaskTransformation(mContext, R.drawable.mask_chat_right))
                        .into(holder.image);
                break;
            }
            case CropTop:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.TOP))
                        .into(holder.image);
                break;
            case CropCenter:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new CropTransformation(mContext, 300, 100))
                        .into(holder.image);
                break;
            case CropBottom:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(
                                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM))
                        .into(holder.image);

                break;
            case CropSquare:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(holder.image);
                break;
            case CropCircle:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(holder.image);
                break;
            case ColorFilter:
//                Glide.with(mContext)
//                        .load(R.drawable.gif_test)
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .bitmapTransform(new CropCircleTransformation(mContext,60,Color.YELLOW))
//                        .into(holder.image);
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(80, 255, 0, 0)))
                        .into(holder.image);
                break;
            case Grayscale:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(holder.image);
                break;
            case RoundedCorners:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 20,
                                LoadConstant.CORNER_BOTTOM))
                        .into(holder.image);
                break;
            case Blur:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new BlurTransformation(mContext, 25))
                        .into(holder.image);
                break;
            case Toon:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case Sepia:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new SepiaFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case Contrast:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new ContrastFilterTransformation(mContext, 2.0f))
                        .into(holder.image);
                break;
            case Invert:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new InvertFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case Pixel:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new PixelationFilterTransformation(mContext, 20))
                        .into(holder.image);
                break;
            case Sketch:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new SketchFilterTransformation(mContext))
                        .into(holder.image);
                break;
            case Swirl:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(
                                new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f)))
                        .into(holder.image);
                break;
            case Brightness:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, 0.5f))
                        .into(holder.image);
                break;
            case Kuawahara:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new KuwaharaFilterTransformation(mContext, 25))
                        .into(holder.image);
                break;
            case Vignette:
                Glide.with(mContext)
                        .load(R.drawable.gif_test)
                        .bitmapTransform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                                new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f))
                        .into(holder.image);
                break;
            default:
                break;
        }
        holder.title.setText(mDataSet.get(position).name());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
