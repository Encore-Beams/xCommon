package cn.encore.xcommon.annotation

import androidx.annotation.LayoutRes

/**
 * 自定义注解 : layoutId 继承 baseActivity & baseFragment 可以添加注解方便添加contentView
 *
 * @LayoutId(R.layout.main)
 * public class MainActivity{}
 *
 * @author: encore
 * Date  : 9/16/20
 * Email : encorebeams@outlook.com
 */
@MustBeDocumented
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class LayoutId(@LayoutRes val resLayoutId: Int){

}



