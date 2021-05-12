package com.amber.imageloader.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import androidx.annotation.NonNull
import com.amber.imageloader.NoLock
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
object LoadUtils {

    private const val PAINT_FLAGS = Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG
    private val DEFAULT_PAINT = Paint(PAINT_FLAGS)


    fun applyMatrix(
        @NonNull inBitmap: Bitmap, @NonNull targetBitmap: Bitmap,
        matrix: Matrix
    ) {
        BITMAP_DRAWABLE_LOCK.lock()
        try {
            val canvas = Canvas(targetBitmap)
            canvas.drawBitmap(inBitmap, matrix, DEFAULT_PAINT)
            clear(canvas)
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock()
        }
    }

    // Avoids warnings in M+.
    private fun clear(canvas: Canvas) {
        canvas.setBitmap(null)
    }

    // See #738.
    private val MODELS_REQUIRING_BITMAP_LOCK: HashSet<String> = HashSet(
        listOf( // Moto X gen 2
            "XT1085",
            "XT1092",
            "XT1093",
            "XT1094",
            "XT1095",
            "XT1096",
            "XT1097",
            "XT1098",  // Moto G gen 1
            "XT1031",
            "XT1028",
            "XT937C",
            "XT1032",
            "XT1008",
            "XT1033",
            "XT1035",
            "XT1034",
            "XT939G",
            "XT1039",
            "XT1040",
            "XT1042",
            "XT1045",  // Moto G gen 2
            "XT1063",
            "XT1064",
            "XT1068",
            "XT1069",
            "XT1072",
            "XT1077",
            "XT1078",
            "XT1079"
        )
    )

    /**
     * https://github.com/bumptech/glide/issues/738 On some devices, bitmap drawing is not thread
     * safe.
     * This lock only locks for these specific devices. For other types of devices the lock is always
     * available and therefore does not impact performance
     */
    private val BITMAP_DRAWABLE_LOCK: Lock =
        if (MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL)) ReentrantLock() else NoLock()

}