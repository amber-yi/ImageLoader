package com.amber.imageloader.listener


import java.lang.Exception

/**
 * @author lsy
 * @date 2021/05/18
 * @description
 */
interface LoadListener<T> {
    /**
     * 加载失败
     */
    fun onLoadFailure(e: Exception?, errorMsg: String)

    /**
     * 加载成功
     * @param resource 目标图片
     * [com.amber.imageloader.ZImageLoader.asBitmap] is [android.graphics.Bitmap]
     * [com.amber.imageloader.ZImageLoader.asFile] is [java.io.File]
     * [com.amber.imageloader.ZImageLoader.downloadOnly] is  [java.io.File]
     * [com.amber.imageloader.ZImageLoader.downloadOnlyTo] is  [java.io.File]
     *
     * other is [android.graphics.drawable.Drawable]
     */
    fun onLoadSuccess(resource: T?)
}