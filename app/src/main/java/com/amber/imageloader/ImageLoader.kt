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

    fun into(imageVew: ImageView, options: ImageLoaderOptions)

    fun into(view: View, options: ImageLoaderOptions)

    /**
     * 预加载，内存与磁盘
     */
    fun preload(options: ImageLoaderOptions)

    fun preload(width: Int, height: Int, options: ImageLoaderOptions)

    fun downloadOnly(options: ImageLoaderOptions)

    fun downloadOnlyTo(path: String, options: ImageLoaderOptions)

    fun downloadOnlyTo(path: String, notify: Boolean, options: ImageLoaderOptions)
}