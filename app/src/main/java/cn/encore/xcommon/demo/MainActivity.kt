package cn.encore.xcommon.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.encore.xcommon.annotation.LayoutId
import cn.encore.xcommon.base.toolbar.BaseToolBarActivity
import cn.encore.xcommon.demo.databinding.ActivityMainBinding

class MainActivity : BaseToolBarActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getToolBarTitle(): String {
        return "MainActivity"
    }

    override fun onInitReady(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


}