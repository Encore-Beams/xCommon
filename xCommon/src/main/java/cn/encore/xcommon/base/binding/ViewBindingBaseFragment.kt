package cn.encore.xcommon.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cn.encore.xcommon.annotation.LayoutId

/**
 * ViewBindingBaseFragment 继承此类可以使用注解 LayoutId(R.layout.xx) 快速加载界面
 * @author: encore
 * Date  : 10/29/20
 * Email : encorebeams@outlook.com
 */
abstract class ViewBindingBaseFragment<V : ViewDataBinding> : Fragment() {
    protected lateinit var mBinding: V

    /**
     * 父类准备完毕后, 子类初始化方法
     */
    protected abstract fun onInitReady(savedInstanceState: Bundle?)

    /**
     * 设置layoutid
     */
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //init binding
        initBinding().also { isSuccess ->
            //初始化完成调用抽象方法通知之类准备完毕
            if (isSuccess) {
                onInitReady(savedInstanceState)
                return initContentView(mBinding.root)
            } else {
                throw NullPointerException("annotation ResLayoutId(R.layout.?) can not be found.")
            }
        }
    }

    /**
     * 子类可以继承此方法, 增加stateview等包裹
     */
    open fun initContentView(rootView: View): View {
        return rootView
    }

    /**
     * init binding
     * @return 是否初始化完成
     */
    private fun initBinding(): Boolean {
        mBinding = DataBindingUtil.inflate(
            layoutInflater, getLayoutId(), null, false
        )
        // 让 LiveData 和 xml 可以双向绑定
        mBinding.lifecycleOwner = this
        return true
    }
}