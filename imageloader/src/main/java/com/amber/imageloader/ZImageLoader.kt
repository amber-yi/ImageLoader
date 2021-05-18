package com.amber.imageloader

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.amber.imageloader.annotation.CornerType
import com.amber.imageloader.config.LoadConfig
import com.amber.imageloader.constant.LoadConstant
import com.amber.imageloader.listener.LoadListener
import com.amber.imageloader.listener.LoadProgressListener
import glide.GlideLoader
import java.io.File

/**
 * @author lsy
 * @date 2021/05/08
 * @description
 */
class ZImageLoader(private val imageLoader: ImageLoader) {

    private val options = ImageLoaderOptions()

    companion object {
        @JvmStatic
        fun with(context: Context): ZImageLoader {
            val glide = GlideLoader()
            glide.bindLife(context)
            return ZImageLoader(glide)
        }

        @JvmStatic
        fun with(context: Activity): ZImageLoader {
            val glide = GlideLoader()
            glide.bindLife(context)
            return ZImageLoader(glide)
        }

        @JvmStatic
        fun with(context: Fragment): ZImageLoader {
            val glide = GlideLoader()
            glide.bindLife(context)
            return ZImageLoader(glide)
        }

        @JvmStatic
        fun with(context: android.app.Fragment): ZImageLoader {
            val glide = GlideLoader()
            glide.bindLife(context)
            return ZImageLoader(glide)
        }

        /**
         * 全局的黑白控制开关，优先级中等，高于单张设置黑白[grayscale]，低于强制设置黑白[forceGrayscale]
         */
        @JvmStatic
        fun updateGlobalGrayscale(isGrayscale: Boolean) {
            if (isGrayscale) {
                LoadConfig.allGrayscaleType = 1
            } else {
                LoadConfig.allGrayscaleType = 2
            }
        }
    }

    /**
     * 加载路径，可以是http网络图片也可以是本地图片路径
     */
    fun load(string: String?): ZImageLoader {
        options.source = string
        return this
    }

    /**
     * 加载file形式的图片
     */
    fun load(file: File): ZImageLoader {
        options.source = file
        return this
    }

    /**
     * 加载二进制的图片
     */
    fun load(bytes: ByteArray): ZImageLoader {
        options.source = bytes
        return this
    }

    /**
     * 加载uri的图片
     */
    fun load(uri: Uri): ZImageLoader {
        options.source = uri
        return this
    }

    /**
     * 加载资源文件图片
     */
    fun load(id: Int): ZImageLoader {
        options.source = id
        return this
    }

    /**
     * 加载网络图片，并且大小为S1级别
     */
    fun loadS1(url: String?): ZImageLoader {
        options.source = ImageUrlUtils.getPicUrlBySize(url, LoadConstant.LARGE)
        return this
    }

    /**
     * 加载网络图片，并且大小为S2级别
     */
    fun loadS2(url: String?): ZImageLoader {
        options.source = ImageUrlUtils.getPicUrlBySize(url, LoadConstant.MIDDLE)
        return this
    }

    /**
     * 加载网络图片，并且大小为S3级别
     */
    fun loadS3(url: String?): ZImageLoader {
        options.source = ImageUrlUtils.getPicUrlBySize(url, LoadConstant.SMALL)
        return this
    }

    /**
     * 加载原始图片，不设置大小级别
     */
    fun loadOriginal(url: String?): ZImageLoader {
        options.source = ImageUrlUtils.getPicUrlBySize(url, LoadConstant.ORIGINAL)
        return this
    }

    fun placeholder(holderId: Int): ZImageLoader {
        options.holderDrawableId = holderId
        return this
    }

    fun placeholder(holder: Drawable): ZImageLoader {
        options.holderDrawable = holder
        return this
    }

    fun placeholder(url: String?): ZImageLoader {
        options.holderDrawableUrl = url
        return this
    }

    fun thumbnail(thumbnail: Float): ZImageLoader {
        options.thumbnail = thumbnail
        return this
    }

    fun error(resourceId: Int): ZImageLoader {
        options.errorDrawableId = resourceId
        return this
    }

    fun error(drawable: Drawable?): ZImageLoader {
        options.errorDrawable = drawable
        return this
    }

    fun asBitmap(): ZImageLoader {
        options.loadAsType = LoadConstant.LOAD_AS_BITMAP
        return this
    }

    fun asGif(): ZImageLoader {
        options.loadAsType = LoadConstant.LOAD_AS_GIF
        return this
    }

    fun asFile(): ZImageLoader {
        options.loadAsType = LoadConstant.LOAD_AS_FILE
        return this
    }

    /**
     * 加载动画，暂不支持
     */
//    fun crossFade():ZImageLoader{
//        options.animationType =
//        return this
//    }


    fun fitXY(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_XY
        return this
    }

    fun fitXY(radius: Float): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_XY
        options.roundRadius = radius
        return this
    }

    fun fitCenter(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_CENTER
        return this
    }

    fun fitCenter(radius: Float): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_CENTER
        options.roundRadius = radius
        return this
    }

    fun fitStart(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_START
        return this
    }

    fun fitStart(radius: Float): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_START
        options.roundRadius = radius
        return this
    }

    fun fitEnd(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_END
        return this
    }

    fun fitEnd(radius: Float): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_FIT_END
        options.roundRadius = radius
        return this
    }
//目前center与centerInside都是采用的fitCenter，暂不开放方法设置，请统一使用fitCenter，如果后续有需求再开放api
//    fun center(): ZImageLoader {
//        options.scaleType = LoadConstant.SCALE_CENTER
//        return this
//    }
//
//    fun centerInside(): ZImageLoader {
//        options.scaleType = LoadConstant.SCALE_CENTER_INSIDE
//        return this
//    }

    fun centerCrop(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_CENTER_CROP
        return this
    }

    fun centerCrop(radius: Float): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_CENTER_CROP
        options.roundRadius = radius
        return this
    }

    fun circle(): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_CIRCLE
        return this
    }

    fun circle(strokeWidth: Float, @ColorInt strokeColor: Int): ZImageLoader {
        options.scaleType = LoadConstant.SCALE_CIRCLE
        options.strokeWidth = strokeWidth
        options.strokeColor = strokeColor
        return this
    }

    fun roundRadius(radius: Float): ZImageLoader {
        options.roundRadius = radius
        return this
    }

    fun roundRadius(radius: Float, @CornerType cornerType: Int): ZImageLoader {
        options.roundRadius = radius
        options.cornerType = cornerType
        return this
    }

    /**
     * 单张图片设置黑白，优先级低于全局设置
     */
    fun grayscale(): ZImageLoader {
        when (LoadConfig.allGrayscaleType) {
            0, 1 -> {
                options.isGrayscale = true
            }
            2 -> {
                options.isGrayscale = false
            }
        }
        return this
    }

    /**
     * 强制设置单张图片黑白类型
     */
    fun forceGrayscale(isGrayscale: Boolean): ZImageLoader {
        options.isGrayscale = isGrayscale
        return this
    }

    /**
     * 高斯模糊，默认25度数，若要调高或调低请调用带参数方法
     */
    fun blur(): ZImageLoader {
        options.blurRadius = 25
        return this
    }

    /**
     * 高斯模糊
     * @param radius  模糊程度，值越高越模糊
     */
    fun blur(radius: Int): ZImageLoader {
        options.blurRadius = 25
        return this
    }

    fun skipMemoryCache(boolean: Boolean): ZImageLoader {
        options.skipMemoryCache = boolean
        return this
    }

    /**
     * 跳过磁盘缓存
     */
    fun diskCacheCacheNone(): ZImageLoader {
        options.diskCacheCache = LoadConstant.DISK_CACHE_NONE
        return this
    }

    /**
     * 将所有数据存入磁盘
     */
    fun diskCacheCacheAll(): ZImageLoader {
        options.diskCacheCache = LoadConstant.DISK_CACHE_ALL
        return this
    }

    /**
     * 将原始数据存入磁盘
     */
    fun diskCacheCacheOriginal(): ZImageLoader {
        options.diskCacheCache = LoadConstant.DISK_CACHE_DATA
        return this
    }

    /**
     * 将变换后的图片存入磁盘
     */
    fun diskCacheCacheResult(): ZImageLoader {
        options.diskCacheCache = LoadConstant.DISK_CACHE_RESOURCE
        return this
    }

    /**
     * 库自行动态存储，http等网络图片存原始数据，本地图片默认不磁盘缓存
     */
    fun diskCacheCacheAuto(): ZImageLoader {
        options.diskCacheCache = LoadConstant.DISK_CACHE_AUTOMATIC
        return this
    }

    fun override(width: Int, height: Int): ZImageLoader {
        options.maxWidth = width
        options.maxHeight = height
        return this
    }

    /**
     *
     * @param listener 加载监听 泛型类型参考如下
     * [asBitmap] is [android.graphics.Bitmap]
     * [asFile] is [java.io.File]
     * [downloadOnly] is  [java.io.File]
     * [downloadOnlyTo] is  [java.io.File]
     * other is [android.graphics.drawable.Drawable]
     */
    fun listener(listener: LoadListener<*>?): ZImageLoader {

        return this
    }

    /**
     * 带进度的加载监听 todo 待完成
     */
    fun listener(listener: LoadProgressListener<*>): ZImageLoader {
        return this
    }

    fun into(imageVew: ImageView) {
        imageLoader.into(imageVew, options)
    }

    fun into(view: View) {
        imageLoader.into(view, options)
    }

    /**
     * 预加载，内存与磁盘
     */
    fun preload() {
        imageLoader.preload(options)
    }

    fun preload(width: Int, height: Int) {
        imageLoader.preload(width, height, options)
    }

    fun downloadOnly() {
        imageLoader.downloadOnly(options)
    }

    fun downloadOnlyTo(path: String) {
        imageLoader.downloadOnlyTo(path, options)
    }

    fun downloadOnlyTo(path: String, notify: Boolean) {
        imageLoader.downloadOnlyTo(path, notify, options)
    }
}