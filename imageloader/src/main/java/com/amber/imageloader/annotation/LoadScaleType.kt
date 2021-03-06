package com.amber.imageloader.annotation

import androidx.annotation.IntDef
import com.amber.imageloader.constant.LoadConstant

/**
 * @author lsy
 * @date 2021/05/06
 * @description
 */
@IntDef(
    LoadConstant.SCALE_FIT_XY,
    LoadConstant.SCALE_FIT_START,
    LoadConstant.SCALE_FIT_CENTER,
    LoadConstant.SCALE_FIT_END,
    LoadConstant.SCALE_CENTER,
    LoadConstant.SCALE_CENTER_CROP,
    LoadConstant.SCALE_CENTER_INSIDE,
    LoadConstant.SCALE_CIRCLE
)
annotation class LoadScaleType
