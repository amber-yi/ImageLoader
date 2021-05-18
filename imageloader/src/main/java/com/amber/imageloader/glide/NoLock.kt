package com.amber.imageloader.glide

import androidx.annotation.NonNull
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
class NoLock internal constructor() : Lock {
    override fun lock() {
        // do nothing
    }

    @Throws(InterruptedException::class)
    override fun lockInterruptibly() {
        // do nothing
    }

    override fun tryLock(): Boolean {
        return true
    }

    @Throws(InterruptedException::class)
    override fun tryLock(time: Long, @NonNull unit: TimeUnit): Boolean {
        return true
    }

    override fun unlock() {
        // do nothing
    }

    @NonNull
    override fun newCondition(): Condition {
        throw UnsupportedOperationException("Should not be called")
    }
}