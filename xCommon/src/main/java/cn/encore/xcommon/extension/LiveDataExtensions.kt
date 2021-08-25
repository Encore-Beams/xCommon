package com.meizu.mcare.utils

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.*
import cn.encore.xcommon.api.ResultState

/**
 * 扩展Live Data 方法: 改变live data 回调, 看需求使用(例如activity被回收,重新启动时), 避免多次调用回调 造成数据混乱
 * Author: encore
 * Date  : 11/16/20
 * Email : encorebeams@outlook.com
 */


/**
 * 单次回调
 */
fun <T> LiveData<T>.observeOnce(owner : LifecycleOwner, observer : (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value : T) {
            observer(value)
            val result = value as ResultState<*>
            if (result is ResultState.COMPLETION) { //当回调结束状态后,移除监听
                removeObserver(this)
            }
        }
    })
}


class ResultBuilder<T>() {
    var onLading : () -> Unit = {}
    var onSuccess : (data : T) -> Unit = {}
    var onError : (ResultState<T>) -> Unit = {}
    var onCompletion : () -> Unit = {}
}

typealias StateLiveData<T> = LiveData<ResultState<T>>
typealias StateMutableLiveData<T> = MutableLiveData<ResultState<T>>

/**
 * 状态回调
 */
@MainThread
inline fun <T> StateLiveData<T>.observeState(owner : LifecycleOwner, init : ResultBuilder<T>.() -> Unit
) {
    val result = ResultBuilder<T>().apply(init)
    observe(owner, Observer { state ->
        when (state) {
            is ResultState.LOADING -> result.onLading.invoke()
            is ResultState.SUCCESS -> result.onSuccess(state.data)
            is ResultState.ERROR -> result.onError(state)
            is ResultState.COMPLETION -> result.onCompletion()
        }
    })
//    observe1(owner) { state ->
//        when (state) {
//            is ResultState.LOADING -> result.onLading.invoke()
//            is ResultState.SUCCESS -> result.onSuccess(state.data)
//            is ResultState.ERROR -> result.onError(state)
//            is ResultState.COMPLETION -> result.onCompletion()
//        }
//    }
}

@MainThread  inline fun <T> LiveData<T>.observe1(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}
