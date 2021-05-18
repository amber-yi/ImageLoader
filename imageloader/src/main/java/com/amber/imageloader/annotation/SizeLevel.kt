package com.amber.imageloader.annotation

import androidx.annotation.IntDef
import com.amber.imageloader.constant.LoadConstant

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
