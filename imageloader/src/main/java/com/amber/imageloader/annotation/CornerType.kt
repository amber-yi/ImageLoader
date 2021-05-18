package com.amber.imageloader.annotation

import androidx.annotation.IntDef
import com.amber.imageloader.constant.LoadConstant

/**
 * @author lsy
 * @date 2021/05/11
 * @description
 */
@IntDef(
    LoadConstant.CORNER_ALL,
    LoadConstant.CORNER_TOP_LEFT,
    LoadConstant.CORNER_TOP_RIGHT,
    LoadConstant.CORNER_BOTTOM_LEFT,
    LoadConstant.CORNER_BOTTOM_RIGHT,
    LoadConstant.CORNER_TOP,
    LoadConstant.CORNER_BOTTOM,
    LoadConstant.CORNER_LEFT,
    LoadConstant.CORNER_RIGHT,
    LoadConstant.CORNER_OTHER_TOP_LEFT,
    LoadConstant.CORNER_OTHER_TOP_RIGHT,
    LoadConstant.CORNER_OTHER_BOTTOM_LEFT,
    LoadConstant.CORNER_OTHER_BOTTOM_RIGHT,
    LoadConstant.CORNER_DIAGONAL_FROM_TOP_LEFT,
    LoadConstant.CORNER_DIAGONAL_FROM_TOP_RIGHT
)
annotation class CornerType
