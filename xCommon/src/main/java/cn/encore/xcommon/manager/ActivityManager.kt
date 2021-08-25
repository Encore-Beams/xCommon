package cn.encore.xcommon.manager

import android.app.Activity
import android.content.Context
import android.util.Log
import java.util.Stack
import kotlin.reflect.KClass
import kotlin.system.exitProcess

/**
 * activity 管理类
 * @author: encore
 * Date  : 2020/6/30
 * Email : encorebeams@outlook.com
 */
class ActivityManager  {

    companion object {
        const val TAG = "ActivityManager"

        private  val activityStack = Stack<Activity>()

        private var instance: ActivityManager? = null
            get() {
                if (field == null) {
                    field = ActivityManager()
                }
                return field
            }

        @Synchronized
        fun get(): ActivityManager {
            return instance!!
        }
    }


    /**
     * 添加activity
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 获取当前最后的activity
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     * 关闭最后一个activity
     */
    fun finishActivity() {
        val activity = activityStack.lastElement()
        removeActivity(activity);
        activity.finish()
    }

    /**
     * 删除activity
     */
    @Synchronized
    fun removeActivity(activity: Activity) {
        activityStack.remove(activity);
    }

    /**
     * 关闭指定的activity
     */
    fun finishActivity(cla: Any) {
        for (activity in activityStack) {
            if (activity.javaClass.name == cla.javaClass.name) {
                activity.finish()
            }
        }
    }

    /**
     * 关闭全部activity
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
    }

    /**
     * 退出app并重启
     */
    fun appExit(context : Context){
        finishAllActivity()
        val activityManager  = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        activityManager.restartPackage(context.packageName);
        exitProcess(0)
    }

}