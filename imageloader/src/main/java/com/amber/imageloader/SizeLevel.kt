package com.amber.imageloader

import androidx.annotation.IntDef

/**
 * @author lsy
 * @date 2021/05/10
 * @description
 */
@IntDef(
    LoadConstant.ORIGINAL,
    LoadConstant.LARGE,
    LoadConstant.MIDDLE,
    LoadConstant.SMALL
)
annotation class SizeLevel
