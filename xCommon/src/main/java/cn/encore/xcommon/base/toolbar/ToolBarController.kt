package cn.encore.xcommon.base.toolbar

import android.animation.LayoutTransition
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import cn.encore.xcommon.R
import cn.encore.xcommon.extension.gone
import cn.encore.xcommon.extension.show


/**
 *
 * Author: encore
 * Date  : 8/10/21
 * Email : encorebeams@outlook.com
 */
open class ToolBarController constructor(private val mActivity: AppCompatActivity) {

    /**
     * toolbar
     */
    private lateinit var mToolBar: Toolbar

    /**
     * 自定义view
     */
    private var mCustomView: View? = null

    /**
     * 显示tab位置 true = toolbar下面 false = toolbar 上面
     */
    private var mIsBelow = true

    /**
     * menu
     */
    private var mMenu: Menu? = null

    /**
     * menu 回调
     */
    private var mOnOptionsMenuListener: OnOptionsMenuListener? = null

    /**
     * 初始化 toolbar
     */
    open fun initToolBar() {
        if (!::mToolBar.isInitialized) {
            val rootView: ViewGroup = mActivity.findViewById(android.R.id.content)
            rootView.getChildAt(0)?.let { targetView ->
                //判断有没自己添加toolbar 没有则主动替换页面的view进行添加
                var toolbar: Toolbar? = targetView.findViewById(R.id.toolbar);
                if (toolbar == null) {
                    //移除targetview
                    rootView.removeView(targetView)

                    val parentView = LinearLayout(mActivity)
                    parentView.orientation = LinearLayout.VERTICAL
                    parentView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    parentView.layoutTransition = LayoutTransition()
                    //添加toolbar
                    toolbar = LayoutInflater.from(mActivity).inflate(R.layout.widget_common_toolbar, rootView, false) as Toolbar
                    //toolbar下的多选模式选中条数layout
                    val multiViewstub = ViewStub(mActivity)
                    multiViewstub.id = R.id.multi_choice_count
                    multiViewstub.layoutResource = R.layout.common_multi_choice_count

                    //toolbar下的导航, 默认隐藏
                    val viewStub = ViewStub(mActivity)
                    viewStub.id = R.id.view_stub_tabbar
                    viewStub.layoutResource = R.layout.common_tabbar_below
//
//                    //toolbar下的多选模式选中条数layout
//                    val actionModeView = ViewStub(mActivity)
//                    actionModeView.id = R.id.action_mode_parent
//                    actionModeView.layoutResource = R.layout.browser_common_action_mode_parent

                    //toolbar 在 content 顶部
                    parentView.addView(toolbar)
                    parentView.addView(multiViewstub)
//                    parentView.addView(viewStub)
                    parentView.addView(targetView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f))
                    //底部添加action mode view
//                    parentView.addView(actionModeView)
                    //添加到content
                    rootView.addView(parentView)
                }
                mToolBar = toolbar;
                mActivity.setSupportActionBar(mToolBar);
                //默认不显示下划线
                setToolBarDivideVisibility(View.GONE)
            }
        }
    }

    /**
     * 显示toolbar
     */
    open fun showToolBar() {
        mToolBar.show()
    }

    /**
     * 隐藏toolBar
     */
    open fun hideToolBar() {
        mToolBar.gone();
    }

    /**
     * 设置标题
     */
    open fun setTitle(title: String) {
        mActivity.supportActionBar?.title = title
//        mToolBar.title = title
    }

    /**
     * 设置副标题
     */
    open fun setSubTitle(subTitle: String) {
        mToolBar.subtitle = subTitle
    }

    /**
     * get toolbar
     */
    open fun getToolBar(): Toolbar {
        return mToolBar
    }

    /**
     * tab 控件是否在 toolbar 下面
     */
    open fun isTabBelow(): Boolean {
        return mIsBelow
    }

    /**
     * Set the action bar into custom navigation mode, supplying a view
     * for custom navigation.
     *
     *
     * Custom navigation views appear between the application icon and
     * any action buttons and may use any space available there. Common
     * use cases for custom navigation views might include an auto-suggesting
     * address bar for a browser or other navigation mechanisms that do not
     * translate well to provided navigation modes.
     *
     * @param view Custom navigation view to place in the toolbar.
     */
    open fun setCustomView(view: View?) {
        if (view != null) {
            if (mCustomView != null) {
                mToolBar.removeView(mCustomView)
            }
            //隐藏标题栏
            setDisplayShowTitleEnabled(false)

            view.layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT)
            //last custom view
            mCustomView = view
            //add custom view
            mToolBar.addView(mCustomView)
        }
    }

    /**
     * Set whether home should be displayed as an "up" affordance.
     * Set this to true if selecting "home" returns up by a single level in your UI
     * rather than back to the top level or front page.
     *
     *
     * To set several display options at once, see the setDisplayOptions methods.
     *
     * @param showHomeAsUp true to show the user that selecting home will return one
     * level up rather than to the top level of the app.
     */
    open fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean) {
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
    }

    /**
     * Set whether home should be displayed as an "up" affordance.
     * Set this to true if selecting "home" returns up by a single level in your UI
     * rather than back to the top level or front page.
     *
     *
     * To set several display options at once, see the setDisplayOptions methods.
     *
     * @param showHomeAsUp true to show the user that selecting home will return one
     * level up rather than to the top level of the app.
     * @param isBackMode   是否后退模式, 自动增加后退按钮, 点击退出
     */
    open fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean, isBackMode: Boolean) {
        mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeAsUp)
        if (isBackMode) {
            mToolBar.setNavigationIcon(R.mipmap.ic_common_back)
            setNavigationOnClickListener { v: View? -> mActivity.finish() }
        }
    }

    /**
     * Set a listener to respond to navigation events.
     *
     *
     * This listener will be called whenever the user clicks the navigation button
     * at the start of the toolbar. An icon must be set for the navigation button to appear.
     *
     * @param listener Listener to set
     */
    open fun setNavigationOnClickListener(listener: View.OnClickListener?) {
        mToolBar.setNavigationOnClickListener(listener)
    }

    /**
     * Set whether an activity title/subtitle should be displayed.
     *
     *
     * To set several display options at once, see the setDisplayOptions methods.
     *
     * @param showTitle true to display a title/subtitle if present.
     */
    open fun setDisplayShowTitleEnabled(showTitle: Boolean) {
        mActivity.supportActionBar?.setDisplayShowTitleEnabled(showTitle)
    }


    /**
     * 设置是否显示toolbar下划线
     *
     * @param visibility
     */
    open fun setToolBarDivideVisibility(visibility: Int) {
        if (View.VISIBLE == visibility) {
            mToolBar.setBackgroundResource(R.drawable.common_titlebar_background_bottom_divide)
        } else {
            mToolBar.setBackgroundResource(R.color.common_toolbar_bg)
        }
    }


    /**
     * toolbar上是否显示tab
     *
     * @param isBelow 是否在toolbar下面显示tab true 是 默认true
     */
    open fun setDisplayShowTabLocation(isBelow: Boolean) {
        mIsBelow = isBelow
    }

    /**
     * 添加tabs
     *
     * @param tabs    tab显示的title
     * @param isReset 是否重置 true 刷新tabs 导航栏
     */
    open fun addTabs(tabs: ArrayList<String>?, isReset: Boolean, viewPager: ViewPager?) {
        if (tabs == null || viewPager == null || tabs.size == 0) {
            return
        }
//        if (mTabBarController == null) {
//            mTabBarController = TabBarController(mActivity, this, viewPager)
//        }
//        mTabBarController.initTabs(tabs, isReset)
    }


    /**
     * 创建menu
     *
     * @param menu
     */
    open fun onCreateOptionsMenu(menu: Menu?) {
        mMenu = menu
    }

    /**
     * menu 点击
     *
     * @param item
     * @return
     */
    open fun onOptionsItemSelected(item: MenuItem): Boolean {
        mOnOptionsMenuListener?.onOptionsItemSelected(item)
        return true
    }

    open fun setOnOptionsMenuListener(onOptionsMenuListener: OnOptionsMenuListener) {
        mOnOptionsMenuListener = onOptionsMenuListener
    }


    /**
     * 获取menu
     *
     * @return
     */
    open fun getMenu(): Menu? {
        return mMenu
    }


    interface OnOptionsMenuListener {
        //menu选择
        fun onOptionsItemSelected(item: MenuItem): Boolean
    }

}