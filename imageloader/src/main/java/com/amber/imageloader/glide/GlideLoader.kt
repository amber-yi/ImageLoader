package glide

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.amber.imageloader.ImageLoader
import com.amber.imageloader.ImageLoaderOptions
import com.amber.imageloader.constant.LoadConstant
import com.amber.imageloader.glide.transformations.*
import com.amber.imageloader.listener.LoadListener

import com.bumptech.glide.*
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import java.io.File
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.*


/**
 * @author lsy
 * @date 2021/05/08
 * @description
 */
class GlideLoader : ImageLoader {
    var requestManager: RequestManager? = null
    var weakLife: WeakReference<Context?>? = null
    override fun bindLife(life: Any?) {
        when (life) {
            is FragmentActivity -> {
                requestManager = Glide.with(life)
                weakLife = WeakReference(life)
            }
            is Activity -> {
                requestManager = Glide.with(life)
                weakLife = WeakReference(life)
            }
            is Fragment -> {
                requestManager = Glide.with(life)
                weakLife = WeakReference(life.activity)
            }
            is android.app.Fragment -> {
                requestManager = Glide.with(life)
                weakLife = WeakReference(life.activity)
            }
            is Context -> {
                requestManager = Glide.with(life)
                weakLife = WeakReference(life)
            }
            else -> {
//                ma = Glide.with(life as Context)
                Log.e("GlideLoader", "glide 加载目前只支持 Activity与Fragment生命周期，请检查！")
            }
        }

    }

    /**
     * 加载图片到imageView
     */
    override fun into(imageVew: ImageView, options: ImageLoaderOptions) {
        handleLoadType(imageVew, options, false, null, false)
    }

    /**
     * 加载图片到view
     * 因为glide不支持直接加载在view上，这里使用target,先获取指定view的宽高
     */
    override fun into(view: View, options: ImageLoaderOptions) {
        view.post {
            var w = view.width
            var h = view.height
            if (w < 0) {
                w = h
            }
            if (h < 0) {
                h = w
            }
            options.maxWidth = w
            options.maxHeight = h
            handleLoadType(view, options, false, null, false)
        }
    }

    override fun preload(options: ImageLoaderOptions) {
        handleLoadType(null, options, false, null, false)
    }

    override fun preload(width: Int, height: Int, options: ImageLoaderOptions) {
        options.maxWidth = width
        options.maxHeight = height
        handleLoadType(null, options, false, null, false)
    }

    override fun downloadOnly(options: ImageLoaderOptions) {
        handleLoadType(null, options, true, null, false)
    }

    override fun downloadOnlyTo(path: String, options: ImageLoaderOptions) {
        handleLoadType(null, options, true, path, false)
    }

    override fun downloadOnlyTo(path: String, notify: Boolean, options: ImageLoaderOptions) {
        handleLoadType(null, options, true, path, true)
    }

    private fun handleLoadType(
        view: View?,
        options: ImageLoaderOptions,
        isDownloadOnly: Boolean,
        path: String?,
        notify: Boolean
    ) {
        requestManager?.let { manager ->
            val request = manager.load(options.source)
            if (isDownloadOnly) {
                loadToFile(options, path, notify, request)
                return
            }
            when (options.loadAsType) {
                LoadConstant.LOAD_AS_DRAWABLE -> {
                    loadToDrawable(view, options, request)
                }
                LoadConstant.LOAD_AS_BITMAP -> {
                    val bitmapRequest = request.asBitmap()
                    loadToBitmap(view, options, bitmapRequest)
                }
                LoadConstant.LOAD_AS_GIF -> {
                    val gifRequest = request.asGif()
                    loadToGif(view, options, gifRequest)
                }
                LoadConstant.LOAD_AS_FILE -> {
                    loadToFile(options, path, notify, request)
                }
                else -> {
                    loadToDrawable(view, options, request)
                }
            }

        }
    }

    private fun loadToBitmap(
        view: View?,
        options: ImageLoaderOptions,
        request: BitmapTypeRequest<Any?>
    ) {
        val life = weakLife?.get()
        life?.let { context ->
            //设置加载占位图
            when {
                options.holderDrawableId > 0 -> {
                    request.placeholder(options.holderDrawableId)
                }
                options.holderDrawable != null -> {
                    request.placeholder(options.holderDrawable)
                }
                //本身图片缩略图为占位图
                options.thumbnail != 0f -> {
                    request.thumbnail(options.thumbnail)
                }
                //其他图片缩略图为占位图
                !TextUtils.isEmpty(options.holderDrawableUrl) -> {
                    val thumbnailRequest: BitmapTypeRequest<String>? =
                        requestManager?.load(options.holderDrawableUrl)?.asBitmap()
                    if (thumbnailRequest != null) {
                        request.thumbnail(thumbnailRequest)
                    }
                }
            }
            //设置加载错误占位图
            if (options.errorDrawableId > 0) {
                request.error(options.errorDrawableId)
            } else if (options.errorDrawable != null) {
                request.error(options.errorDrawable)
            }
            //设置内存缓存策略
            request.skipMemoryCache(options.skipMemoryCache)
            //设置磁盘缓存策略
            when (options.diskCacheCache) {
                LoadConstant.DISK_CACHE_ALL -> {
                    request.diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                LoadConstant.DISK_CACHE_NONE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                LoadConstant.DISK_CACHE_DATA -> {
                    request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                }
                LoadConstant.DISK_CACHE_RESOURCE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.RESULT)
                }
                LoadConstant.DISK_CACHE_AUTOMATIC -> {
                    if (options.source is String && (options.source as String).startsWith("http")) {
                        request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    } else {
                        request.diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
            }
            //设置加载的图片大小
            if (options.maxWidth > 0 && options.maxHeight > 0) {
                request.override(options.maxWidth, options.maxHeight)
            }
            val transformations: ArrayList<BitmapTransformation> = ArrayList()
            //设置缩放
            when (options.scaleType) {
                LoadConstant.SCALE_FIT_XY -> {
                    transformations.add(FitXY(context))
                }
                LoadConstant.SCALE_FIT_START -> {
                    transformations.add(FitStart(context))
                }
                LoadConstant.SCALE_FIT_CENTER,
                LoadConstant.SCALE_CENTER,
                LoadConstant.SCALE_CENTER_INSIDE -> {
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_FIT_END -> {
                    if (view is ImageView) {
                        view.scaleType = ImageView.ScaleType.FIT_END
                    }
                    //尾部对齐暂时使用fitCenter，imageView设置scaleType实现，View可通过后续看是否真的有如此需求
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_CENTER_CROP -> {
                    transformations.add(CenterCrop(context))
                }
                //圆形图片，可能有边框
                LoadConstant.SCALE_CIRCLE -> {
                    transformations.add(
                        CropCircleTransformation(
                            context,
                            options.strokeWidth,
                            options.strokeColor
                        )
                    )
                }
                else -> {
                    transformations.add(FitCenter(context))
                }
            }
            //设置黑白
            if (options.isGrayscale) {
                transformations.add(GrayscaleTransformation(context))
            }
            //...后续添加更多trans

            //设置圆角 圆角设置放置最后，避免被其它覆盖
            if (options.roundRadius != 0f) {
                transformations.add(
                    RoundedCornersTransformation(
                        context,
                        options.roundRadius,
                        0,
                        options.cornerType
                    )
                )
            }
            if (transformations.size > 0) {
                val multiTrans = MultiTransformation(*transformations.toTypedArray())
                request.transform(multiTrans)
            }
            request.listener(object : RequestListener<Any?, Bitmap> {
                override fun onException(
                    e: Exception?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.onLoadFailure(e, e?.message ?: "")
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.let {
                        try {
                            (options.listener as LoadListener<Bitmap>).onLoadSuccess(resource)
                        } catch (e: Exception) {
                            Log.e("GlideLoader", "loadToBitmap LoadListener type is error")
                        }
                    }
                    return false
                }
            })
            when {
                view is ImageView -> {
                    request.into(view)
                }
                view is Target<*> -> {
                    try {
                        request.into(view as Target<Bitmap>)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(
                            "GlideLoader",
                            "View target type is not bitmap,maybe should remove asBitmap()"
                        )
                    }
                }
                view != null -> {
                    request.into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap?,
                            glideAnimation: GlideAnimation<in Bitmap>?
                        ) {
                            resource?.let { bit ->
                                view.setBackgroundDrawable(BitmapDrawable(bit))
                            }
                        }

                    })
                }
                else -> {
                    request.preload()
                }
            }
        }
    }

    private fun loadToGif(view: View?, options: ImageLoaderOptions, request: GifTypeRequest<Any?>) {
        val life = weakLife?.get()
        life?.let { context ->
            //设置加载占位图
            when {
                options.holderDrawableId > 0 -> {
                    request.placeholder(options.holderDrawableId)
                }
                options.holderDrawable != null -> {
                    request.placeholder(options.holderDrawable)
                }
                //本身图片缩略图为占位图
                options.thumbnail != 0f -> {
                    request.thumbnail(options.thumbnail)
                }
                //其他图片缩略图为占位图
                !TextUtils.isEmpty(options.holderDrawableUrl) -> {
                    val thumbnailRequest: GifRequestBuilder<String>? =
                        requestManager?.load(options.holderDrawableUrl)?.asGif()
                    if (thumbnailRequest != null) {
                        request.thumbnail(thumbnailRequest)
                    }
                }
            }
            //设置加载错误占位图
            if (options.errorDrawableId > 0) {
                request.error(options.errorDrawableId)
            } else if (options.errorDrawable != null) {
                request.error(options.errorDrawable)
            }
            //设置内存缓存策略
            request.skipMemoryCache(options.skipMemoryCache)
            //设置磁盘缓存策略
            when (options.diskCacheCache) {
                LoadConstant.DISK_CACHE_ALL -> {
                    request.diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                LoadConstant.DISK_CACHE_NONE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                LoadConstant.DISK_CACHE_DATA -> {
                    request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                }
                LoadConstant.DISK_CACHE_RESOURCE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.RESULT)
                }
                LoadConstant.DISK_CACHE_AUTOMATIC -> {
                    if (options.source is String && (options.source as String).startsWith("http")) {
                        request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    } else {
                        request.diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
            }
            //设置加载的图片大小
            if (options.maxWidth > 0 && options.maxHeight > 0) {
                request.override(options.maxWidth, options.maxHeight)
            }
            val transformations: ArrayList<BitmapTransformation> = ArrayList()
            //设置缩放
            when (options.scaleType) {
                LoadConstant.SCALE_FIT_XY -> {
                    transformations.add(FitXY(context))
                }
                LoadConstant.SCALE_FIT_START -> {
                    transformations.add(FitStart(context))
                }
                LoadConstant.SCALE_FIT_CENTER,
                LoadConstant.SCALE_CENTER,
                LoadConstant.SCALE_CENTER_INSIDE -> {
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_FIT_END -> {
                    if (view is ImageView) {
                        view.scaleType = ImageView.ScaleType.FIT_END
                    }
                    //尾部对齐暂时使用fitCenter，imageView设置scaleType实现，View可通过后续看是否真的有如此需求
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_CENTER_CROP -> {
                    transformations.add(CenterCrop(context))
                }
                //圆形图片，可能有边框
                LoadConstant.SCALE_CIRCLE -> {
                    transformations.add(
                        CropCircleTransformation(
                            context,
                            options.strokeWidth,
                            options.strokeColor
                        )
                    )
                }
                else -> {
                    transformations.add(FitCenter(context))
                }
            }

            //设置黑白
            if (options.isGrayscale) {
                transformations.add(GrayscaleTransformation(context))
            }
            //...后续添加更多trans
            //设置圆角
            if (options.roundRadius != 0f) {
                transformations.add(
                    RoundedCornersTransformation(
                        context,
                        options.roundRadius,
                        0,
                        options.cornerType
                    )
                )
            }
            if (transformations.size > 0) {
                val multiTrans = MultiTransformation(*transformations.toTypedArray())
                request.transformFrame(multiTrans)
            }

            request.listener(object : RequestListener<Any?, GifDrawable> {
                override fun onException(
                    e: Exception?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.onLoadFailure(e, e?.message ?: "")
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.let {
                        try {
                            (options.listener as LoadListener<Drawable>).onLoadSuccess(resource)
                        } catch (e: Exception) {
                            Log.e("GlideLoader", "loadToGif LoadListener type is error ")
                        }
                    }
                    return false
                }

            })
            when {
                view is ImageView -> {
                    request.into(view)
                }
                view is Target<*> -> {
                    try {
                        request.into(view as Target<GifDrawable>)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(
                            "GlideLoader",
                            "View target type is not GifDrawable,maybe should remove asGif() "
                        )
                    }
                }
                view != null -> {
                    request.into(object : SimpleTarget<GifDrawable>() {
                        override fun onResourceReady(
                            resource: GifDrawable?,
                            glideAnimation: GlideAnimation<in GifDrawable>?
                        ) {
                            resource?.let { bit ->
                                view.setBackgroundDrawable(bit)
                            }
                        }

                    })
                }
                else -> {
                    request.preload()
                }
            }
        }
    }

    private fun loadToDrawable(
        view: View?,
        options: ImageLoaderOptions,
        request: DrawableTypeRequest<Any?>
    ) {
        val life = weakLife?.get()
        life?.let { context ->
            //设置加载占位图
            when {
                options.holderDrawableId > 0 -> {
                    request.placeholder(options.holderDrawableId)
                }
                options.holderDrawable != null -> {
                    request.placeholder(options.holderDrawable)
                }
                //本身图片缩略图为占位图
                options.thumbnail != 0f -> {
                    request.thumbnail(options.thumbnail)
                }
                //其他图片缩略图为占位图
                !TextUtils.isEmpty(options.holderDrawableUrl) -> {
                    val thumbnailRequest: DrawableTypeRequest<String>? =
                        requestManager?.load(options.holderDrawableUrl)
                    if (thumbnailRequest != null) {
                        request.thumbnail(thumbnailRequest)
                    }
                }
            }
            //设置加载错误占位图
            if (options.errorDrawableId > 0) {
                request.error(options.errorDrawableId)
            } else if (options.errorDrawable != null) {
                request.error(options.errorDrawable)
            }
            //设置内存缓存策略
            request.skipMemoryCache(options.skipMemoryCache)
            //设置磁盘缓存策略
            when (options.diskCacheCache) {
                LoadConstant.DISK_CACHE_ALL -> {
                    request.diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                LoadConstant.DISK_CACHE_NONE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                LoadConstant.DISK_CACHE_DATA -> {
                    request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                }
                LoadConstant.DISK_CACHE_RESOURCE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.RESULT)
                }
                LoadConstant.DISK_CACHE_AUTOMATIC -> {
                    if (options.source is String && (options.source as String).startsWith("http")) {
                        request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    } else {
                        request.diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
            }
            //设置加载的图片大小
            if (options.maxWidth > 0 && options.maxHeight > 0) {
                request.override(options.maxWidth, options.maxHeight)
            }
            val transformations: ArrayList<BitmapTransformation> = ArrayList()
            //设置缩放
            when (options.scaleType) {
                LoadConstant.SCALE_FIT_XY -> {
                    transformations.add(FitXY(context))
                }
                LoadConstant.SCALE_FIT_START -> {
                    transformations.add(FitStart(context))
                }
                LoadConstant.SCALE_FIT_CENTER,
                LoadConstant.SCALE_CENTER,
                LoadConstant.SCALE_CENTER_INSIDE -> {
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_FIT_END -> {
                    if (view is ImageView) {
                        view.scaleType = ImageView.ScaleType.FIT_END
                    }
                    //尾部对齐暂时使用fitCenter，imageView设置scaleType实现，View可通过后续看是否真的有如此需求
                    transformations.add(FitCenter(context))
                }
                LoadConstant.SCALE_CENTER_CROP -> {
                    transformations.add(CenterCrop(context))
                }
                //圆形图片，可能有边框
                LoadConstant.SCALE_CIRCLE -> {
                    transformations.add(
                        CropCircleTransformation(
                            context,
                            options.strokeWidth,
                            options.strokeColor
                        )
                    )
                }
                else -> {
                    transformations.add(FitCenter(context))
                }
            }

            //设置黑白
            if (options.isGrayscale) {
                transformations.add(GrayscaleTransformation(context))
            }
            //高斯模糊
            if (options.blurRadius > 0) {
                transformations.add(BlurTransformation(context, options.blurRadius))
            }
            //...后续添加更多trans

            //设置圆角
            if (options.roundRadius != 0f) {
                transformations.add(
                    RoundedCornersTransformation(
                        context,
                        options.roundRadius,
                        0,
                        options.cornerType
                    )
                )
            }
            if (transformations.size > 0) {
                val multiTrans = MultiTransformation(*transformations.toTypedArray())
                request.bitmapTransform(multiTrans)
            }

            request.listener(object : RequestListener<Any?, GlideDrawable> {
                override fun onException(
                    e: Exception?,
                    model: Any?,
                    target: Target<GlideDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.onLoadFailure(e, e?.message ?: "")
                    return false
                }

                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: Any?,
                    target: Target<GlideDrawable>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.let {
                        try {
                            (options.listener as LoadListener<Drawable>).onLoadSuccess(resource)
                        } catch (e: Exception) {
                            Log.e("GlideLoader", "loadToGif LoadListener type is error ")
                        }
                    }
                    return false
                }

            })
            when {
                view is ImageView -> {
                    request.into(view)
                }
                view is Target<*> -> {
                    try {
                        request.into(view as Target<GlideDrawable>)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(
                            "GlideLoader",
                            "View target type is not bitmap,maybe should remove asBitmap()"
                        )
                    }
                }
                view != null -> {
                    val target = request.into(object : SimpleTarget<GlideDrawable?>() {
                        override fun onResourceReady(
                            resource: GlideDrawable?,
                            glideAnimation: GlideAnimation<in GlideDrawable?>?
                        ) {
                            resource?.let { bit ->
                                view.setBackgroundDrawable(bit)
                            }
                        }

                    })
                }
                else -> {
                    request.preload()
                }
            }
        }
    }

    private fun loadToFile(
        options: ImageLoaderOptions,
        path: String?,
        notify: Boolean,
        request: DrawableTypeRequest<Any?>
    ) {
        val life = weakLife?.get()
        life?.let { context ->
            //设置内存缓存策略
            request.skipMemoryCache(true)
            //设置磁盘缓存策略
            when (options.diskCacheCache) {
                LoadConstant.DISK_CACHE_ALL -> {
                    request.diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                LoadConstant.DISK_CACHE_NONE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.NONE)
                }
                LoadConstant.DISK_CACHE_DATA -> {
                    request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                }
                LoadConstant.DISK_CACHE_RESOURCE -> {
                    request.diskCacheStrategy(DiskCacheStrategy.RESULT)
                }
                LoadConstant.DISK_CACHE_AUTOMATIC -> {
                    if (options.source is String && (options.source as String).startsWith("http")) {
                        request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    } else {
                        request.diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                }
            }
            //设置加载的图片大小
            if (options.maxWidth > 0 && options.maxHeight > 0) {
                request.override(options.maxWidth, options.maxHeight)
            }

            val transformations: ArrayList<BitmapTransformation> = ArrayList()
            //设置黑白
            if (options.isGrayscale) {
                transformations.add(GrayscaleTransformation(context))
            }
            //高斯模糊
            if (options.blurRadius > 0) {
                transformations.add(BlurTransformation(context, options.blurRadius))
            }
            //...后续添加更多trans

            //设置圆角
            if (options.roundRadius != 0f) {
                transformations.add(
                    RoundedCornersTransformation(
                        context,
                        options.roundRadius,
                        0,
                        options.cornerType
                    )
                )
            }
            if (transformations.size > 0) {
                val multiTrans = MultiTransformation(*transformations.toTypedArray())
                request.bitmapTransform(multiTrans)
            }
            request.listener(object : RequestListener<Any?, GlideDrawable> {
                override fun onException(
                    e: Exception?,
                    model: Any?,
                    target: Target<GlideDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    options.listener?.onLoadFailure(e, e?.message ?: "")
                    return false
                }

                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: Any?,
                    target: Target<GlideDrawable>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            request.downloadOnly(object : SimpleTarget<File?>() {
                override fun onResourceReady(
                    resource: File?,
                    glideAnimation: GlideAnimation<in File?>?
                ) {
                    if (resource == null) {
                        //下载文件为空
                        Log.e(
                            "GlideLoader",
                            "Download failed, file is null"
                        )
                        return
                    }
                    if (!resource.exists()) {
                        //下载文件不存在
                        Log.e(
                            "GlideLoader",
                            "Download failed, file does not exist"
                        )
                        return
                    }
                    if (path == null || path == "") {
                        //下载在glide默认地址，不需要复制到其他地方
                        options.listener?.let {
                            try {
                                (options.listener as LoadListener<File>).onLoadSuccess(resource)
                            } catch (e: Exception) {
                                Log.e("GlideLoader", "loadToFile LoadListener type is error ")
                            }
                        }
                        return
                    }

                    val file = resource.copyTo(File(path))
                    if (notify) {
                        val values = ContentValues()
                        values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*")
                        context.contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                        )
                    }
                    options.listener?.let {
                        try {
                            (options.listener as LoadListener<File>).onLoadSuccess(file)
                        } catch (e: Exception) {
                            Log.e("GlideLoader", "loadToFile LoadListener type is error ")
                        }
                    }
                }
            })
        }
    }


}