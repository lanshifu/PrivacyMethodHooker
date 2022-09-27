package com.lanshifu.privacymethodhooker.testcase

import android.app.ActivityManager
import android.content.Context

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object ActivityManagerUtil {


    fun getRunningAppProcesses(context: Context): MutableList<ActivityManager.RunningAppProcessInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.runningAppProcesses
    }

    fun getRunningServices(context: Context): MutableList<ActivityManager.RunningServiceInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(100)
    }

    fun getRecentTasks(context: Context): MutableList<ActivityManager.RecentTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRecentTasks(100, ActivityManager.RECENT_WITH_EXCLUDED)
    }

    fun getRunningTasks(context: Context): MutableList<ActivityManager.RunningTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningTasks(100)
    }

}