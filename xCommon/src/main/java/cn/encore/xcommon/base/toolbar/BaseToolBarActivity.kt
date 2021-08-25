package cn.encore.xcommon.base.toolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.databinding.ViewDataBinding
import cn.encore.xcommon.base.binding.ViewBindingBaseActivity

/**
 * 处理 toolbar 操作的activity
 * @author: encore
 * Date  : 8/6/21
 * Email : encorebeams@outlook.com
 */
abstract class BaseToolBarActivity<V : ViewDataBinding> : ViewBindingBaseActivity<V>() {

    private lateinit var mToolBarController: ToolBarController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //toolbar 初始化
        if (isAddToolBar()) {
            if (!::mToolBarController.isInitialized) {
                mToolBarController = ToolBarController(this)
            }
            mToolBarController.initToolBar()
            mToolBarController.setTitle(getToolBarTitle())
        }
    }


    fun getToolBarController(): ToolBarController {
        return mToolBarController
    }

    /**
     * 是否添加ToolBar 默认添加, 继承此方法可修改, 也可以手动添加 并增加id R.id.toolbar
     */
    open fun isAddToolBar(): Boolean {
        return true;
    }

    /**
     * 获取标题
     */
    abstract fun getToolBarTitle(): String;

    /**
     *
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isAddToolBar()) {
            mToolBarController.onCreateOptionsMenu(menu)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (isAddToolBar() && mToolBarController.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }
}