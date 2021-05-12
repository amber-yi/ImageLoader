package com.amber.imageloader

/**
 * @author lsy
 * @date 2021/05/10
 * @description
 */
object ImageUrlUtils {
    fun getPicUrlBySize(picUrl: String?, @SizeLevel size: Int): String {
        picUrl?.let {
            val x = it.lastIndexOf(".")
            if (x != -1) {
                var prefix = it.substring(0, x)
                val subfix = it.substring(x + 1, it.length)
                if (prefix.endsWith("_s1") || prefix.endsWith("_s2") || prefix.endsWith("_s3")) {
                    prefix = prefix.substring(0, prefix.length - 3)
                }
                var url = when (size) {
                    LoadConstant.LARGE -> {
                        prefix + "_s1." + subfix
                    }
                    LoadConstant.MIDDLE -> {
                        prefix + "_s2." + subfix
                    }
                    LoadConstant.SMALL -> {
                        prefix + "_s3." + subfix
                    }

                    else -> {
                        prefix + subfix
                    }
                }
                // 如果是相对地址，拼接成绝对地址
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = LoadConstant.BASE_IGM + url
                }
            }
            return ""
        }
        return ""
    }
}