package com.amber.imageloader

import androidx.annotation.IntDef

/**
 * @author lsy
 * @date 2021/05/06
 * @description
 */
@IntDef(
    LoadConstant.LOAD_AS_DRAWABLE,
    LoadConstant.LOAD_AS_BITMAP,
    LoadConstant.LOAD_AS_GIF,
    LoadConstant.LOAD_AS_FILE
)
annotation class LoadType
