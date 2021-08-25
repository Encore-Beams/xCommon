package cn.encore.xcommon.extension

import timber.log.Timber

/**
 * 打印log扩展函数
 * Author: encore
 * Date  : 8/6/21
 * Email : encorebeams@outlook.com
 */
fun Any.logD(message: String) {
    Timber.d(message)
}

fun Any.logD(tag: String, message: String) {
    Timber.tag(tag).d(message)
}