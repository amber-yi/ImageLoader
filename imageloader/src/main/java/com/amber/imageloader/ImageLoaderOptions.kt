package com.amber.imageloader

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 * @author lsy
 * @date 2021/05/06
 * 图片库参数构建类
 */
class ImageLoaderOptions {

    /**
     * 加载目标 目前支持url，本地文件，uri，
     */
    var source: Any? = null

    /**
     * 加载中占位图资源id
     */
    var holderDrawableId = -1

    /**
     * 加载中占位图
     */
    var holderDrawable: Drawable? = null

    /**
     * 加载中的占位图
     */
    var holderDrawableUrl: String? = null

    /**
     * 占位图-缩略图的倍数
     */
    var thumbnail: Float = 0.0f

    /**
     * 加载错误的占位图资源id
     */
    var errorDrawableId = -1

    /**
     * 加载错误的占位图
     */
    var errorDrawable: Drawable? = null

    /**
     * 加载类型 默认为drawable
     */
    @com.amber.imageloader.LoadType
    var loadAsType = com.amber.imageloader.LoadConstant.LOAD_AS_DRAWABLE

    /**
     * 加载完成展示的动画类型，默认无动画
     */
    var animationType = 0

    /**
     * 缩放类型 默认fit_center
     */
    @com.amber.imageloader.LoadScaleType
    var scaleType = com.amber.imageloader.LoadConstant.SCALE_FIT_CENTER

    /**
     * 边框大小
     */
    var strokeWidth = 0f

    /**
     * 边框颜色
     */
    @ColorInt
    var strokeColor = 0

    /**
     * 是否跳过内存缓存 默认不跳过
     * 同一资源所加载策略应该一样
     * 如果第一次不跳过内存缓存，第二次加载设置跳过内存缓存不会清除之前内存缓存
     */
    var skipMemoryCache = false

    /**
     * 磁盘缓存类型
     * 同一资源所加载策略应该一样
     * 如果第一次不跳过磁盘缓存，第二次加载设置跳过磁盘缓存不会清除之前磁盘缓存
     */
    var diskCacheCache = com.amber.imageloader.LoadConstant.DISK_CACHE_AUTOMATIC

    /**
     *最大宽度，默认不限制
     */
    var maxWidth = -1

    /**
     *最大高度，默认不限制
     */
    var maxHeight = -1

    /**
     * 圆角 默认不设置
     */
    var roundRadius = 0f

    /**
     * 圆角类型，默认八个方向全部都有圆角
     */
    @CornerType
    var cornerType = com.amber.imageloader.LoadConstant.CORNER_ALL

    /**
     * 是否是黑白
     */
    var isGrayscale=false

    /**
     * 高斯模糊度数 默认0，没有高斯模糊，值越大越模糊
     */
    var blurRadius=0
}