package cn.encore.xcommon.base.binding

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.encore.xcommon.annotation.LayoutId
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 * ViewBindingBaseActivity 继承此类可以使用注解 LayoutId(R.layout.xx) 快速加载界面
 * @author: encore
 * Date  : 9/17/20
 * Email : encorebeams@outlook.com
 */
abstract class ViewBindingBaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //immersionBar Init
        immersionBarInit();
        //init binding
        initBinding().also { isSuccess ->
            //初始化完成调用抽象方法通知之类准备完毕
            if (isSuccess) {
                onInitReady(savedInstanceState)
            } else {
                throw NullPointerException("annotation LayoutId(R.layout.?) can not be found.")
            }
        }

    }


    /**
     * 父类准备完毕后, 子类初始化方法
     */
    protected abstract fun onInitReady(savedInstanceState: Bundle?)

    /**
     * 设置layoutid
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化contentView, 子类可以重载此方法增加stateView,toolbar等
     */
    open fun initContentView(rootView: View) {
        setContentView(rootView)
    }


    /**
     * init binding
     * @return 是否初始化完成
     */
    private fun initBinding(): Boolean {
        //如果设置了资源id初始化显示,并设置dataBing
        mBinding = DataBindingUtil.inflate(
            layoutInflater, getLayoutId(), null, false
        )
        //初始化contentView, 子类可以重载此方法增加stateView,toolbar等
        initContentView(mBinding.root)
        // 让 LiveData 和 xml 可以双向绑定
        mBinding.lifecycleOwner = this
        return true
    }

    /**
     *  onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    /**
     * 初始化immersionBar 处理statusbar等信息, 子类可继承自定义处理
     */
    open fun immersionBarInit() {
        //沉浸式状态栏
        ImmersionBar.with(this)
            .barColor(android.R.color.white)
            .statusBarDarkFont(true)
            .init();
    }
}