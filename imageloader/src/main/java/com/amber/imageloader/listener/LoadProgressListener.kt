package com.amber.imageloader.listener

import androidx.annotation.IntRange

/**
 * @author lsy
 * @date 2021/05/18
 * @description
 */
interface LoadProgressListener<T> : LoadListener<T> {
    fun onProgress(@IntRange(from = 0, to = 100) progress: Int)
}