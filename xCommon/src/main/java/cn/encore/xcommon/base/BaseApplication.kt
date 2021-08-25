package cn.encore.xcommon.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.encore.xcommon.BuildConfig
import cn.encore.xcommon.manager.ActivityManager
import com.tencent.mmkv.MMKV
import timber.log.Timber

/**
 *
 * Author: encore
 * Date  : 2020/6/30
 * Email : encorebeams@outlook.com
 */
open class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        initApp(this)
        // 主进程初始化
        initResource(this)
        //log
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        //初始化 mmkv 存储框架
        MMKV.initialize(this)

    }

    companion object {
        private lateinit var app: Application

        @JvmStatic
        fun initApp(app: Application) {
            Companion.app = app
        }

        private fun initResource(app: Application) {
            // 监听所有 Activity 的创建和销毁
            app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity) {
                }

                override fun onActivityStarted(activity: Activity) {
                }

                override fun onActivityDestroyed(activity: Activity) {
                    ActivityManager.get().removeActivity(activity)
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivityCreated(
                        activity: Activity,
                        savedInstanceState: Bundle?
                ) {
                    ActivityManager.get().addActivity(activity)
                }

                override fun onActivityResumed(activity: Activity) {
                }

            })
        }

        @JvmStatic
        fun getInstance(): Application {
            return app
        }
    }


}