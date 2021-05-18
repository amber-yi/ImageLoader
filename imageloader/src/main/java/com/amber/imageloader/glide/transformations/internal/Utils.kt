package com.amber.imageloader.glide.transformations.internal

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log

/**
 * Copyright (C) 2017 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
object Utils {
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun getMaskDrawable(context: Context, maskId: Int): Drawable? {
        val drawable: Drawable? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(maskId)
        } else {
            context.resources.getDrawable(maskId)
        }
        if(drawable==null){
            Log.e("Utils","maskId is invalid")
        }
        return drawable
    }

    fun toDp(px: Int): Int {
        return px * Resources.getSystem().displayMetrics.density.toInt()
    }
}