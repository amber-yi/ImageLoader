package com.amber.imageloader.constant

/**
 * @author lsy
 * @date 2021/05/06
 * @description
 */
object LoadConstant {
    //======================================加载类型 start===========================================//
    /**
     * 加载为drawable类型
     */
    const val LOAD_AS_DRAWABLE = 0

    /**
     * 加载为bitmap类型
     */
    const val LOAD_AS_BITMAP = 1

    /**
     * 加载为gif类型
     */
    const val LOAD_AS_GIF = 2

    /**
     * 加载为文件类型
     */
    const val LOAD_AS_FILE = 3

    //======================================加载类型 end=============================================//

    //======================================缩放类型 start===========================================//

    /**
     * 缩放类型请参考[android.widget.ImageView.ScaleType]
     */
    const val SCALE_FIT_XY = 1
    const val SCALE_FIT_START = 2
    const val SCALE_FIT_CENTER = 3
    const val SCALE_FIT_END = 4
    const val SCALE_CENTER = 5
    const val SCALE_CENTER_CROP = 6
    const val SCALE_CENTER_INSIDE = 7

    /**
     * 加载为圆形图片
     */
    const val SCALE_CIRCLE = 8
    //=====================================缩放类型 end==============================================//

    //======================================磁盘缓存策略 start=======================================//
    /**
     * 所有资源都缓存进磁盘
     */
    const val DISK_CACHE_ALL = "disk_cache_all"

    /**
     * 所有资源都不缓存进磁盘
     */
    const val DISK_CACHE_NONE = "disk_cache_none"

    /**
     * 在资源解码前就将原始数据写入磁盘缓存
     */
    const val DISK_CACHE_DATA = "disk_cache_data"

    /**
     * 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源
     */
    const val DISK_CACHE_RESOURCE = "disk_cache_resource"

    /**
     * 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     * 目前glide3.x版本 http等网络图片采用DISK_CACHE_DATA，本地图片采用DISK_CACHE_NONE
     */
    const val DISK_CACHE_AUTOMATIC = "disk_cache_automatic"
    //======================================磁盘缓存策略 end=========================================//

    //======================================图片大小级别 start=======================================//
    /**
     * 图片路径前缀
     */
    const val BASE_IGM = "https://image.zhihuishu.com/"

    /**
     * 原始 不设置级别
     */
    const val ORIGINAL = 0

    /**
     * 大级别
     */
    const val LARGE = 1

    /**
     * 中级别
     */
    const val MIDDLE = 2

    /**
     * 小级别
     */
    const val SMALL = 3
    //=====================================图片大小级别 end==========================================//

    //======================================圆角位置 start==========================================//
    const val CORNER_ALL = 0
    const val CORNER_TOP_LEFT = 1
    const val CORNER_TOP_RIGHT = 2
    const val CORNER_BOTTOM_LEFT = 3
    const val CORNER_BOTTOM_RIGHT = 4
    const val CORNER_TOP = 5
    const val CORNER_BOTTOM = 6
    const val CORNER_LEFT = 7
    const val CORNER_RIGHT = 8
    const val CORNER_OTHER_TOP_LEFT = 9
    const val CORNER_OTHER_TOP_RIGHT = 10
    const val CORNER_OTHER_BOTTOM_LEFT = 11
    const val CORNER_OTHER_BOTTOM_RIGHT = 12
    const val CORNER_DIAGONAL_FROM_TOP_LEFT = 13
    const val CORNER_DIAGONAL_FROM_TOP_RIGHT = 14
    //======================================圆角位置 end==========================================//
}