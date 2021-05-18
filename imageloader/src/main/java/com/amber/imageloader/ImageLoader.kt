package com.amber.imageloader

import android.view.View
import android.widget.ImageView

/**
 * @author lsy
 * @date 2021/05/08
 * @description
 */
interface ImageLoader {
    /**
     * 绑定生命周期 目前只支持activity和fragment，view在glide升级到4.0版本后支持
     */
    fun bindLife(life: Any?)

    fun into(imageVew: ImageView, options: com.amber.imageloader.ImageLoaderOptions)

    fun into(view: View, options: com.amber.imageloader.ImageLoaderOptions)

    /**
     * 预加载，内存与磁盘
     */
    fun preload(options: com.amber.imageloader.ImageLoaderOptions)

    fun preload(width: Int, height: Int, options: com.amber.imageloader.ImageLoaderOptions)

    fun downloadOnly(options: com.amber.imageloader.ImageLoaderOptions)

    fun downloadOnlyTo(path: String, options: com.amber.imageloader.ImageLoaderOptions)

    fun downloadOnlyTo(path: String, notify: Boolean, options: com.amber.imageloader.ImageLoaderOptions)
}