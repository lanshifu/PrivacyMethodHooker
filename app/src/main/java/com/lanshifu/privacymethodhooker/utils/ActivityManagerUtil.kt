package com.lanshifu.privacymethodhooker.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.lang.reflect.Method

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

    fun getRunningAppProcessesByReflect(context: Context): MutableList<ActivityManager.RunningAppProcessInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val clazz = Class.forName("android.app.ActivityManager")
        val method: Method = clazz.getDeclaredMethod("getRunningAppProcesses")
        method.isAccessible = true
        return method.invoke(manager) as MutableList<ActivityManager.RunningAppProcessInfo?>?
    }

    fun getRunningServices(context: Context): MutableList<ActivityManager.RunningServiceInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(100)
    }

    fun getRunningServicesByReflect(context: Context): MutableList<ActivityManager.RunningServiceInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val clazz = Class.forName("android.app.ActivityManager")
        val method: Method = clazz.getDeclaredMethod("getRunningServices", Int::class.java)
        method.isAccessible = true
        return method.invoke(manager, 100) as MutableList<ActivityManager.RunningServiceInfo?>?
    }

    fun getRecentTasks(context: Context): MutableList<ActivityManager.RecentTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRecentTasks(100, ActivityManager.RECENT_WITH_EXCLUDED)
    }

    fun getRecentTasksByReflect(context: Context): MutableList<ActivityManager.RecentTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val clazz = Class.forName("android.app.ActivityManager")
        val method: Method =
            clazz.getDeclaredMethod("getRecentTasks", Int::class.java, Int::class.java)
        method.isAccessible = true
        return method.invoke(
            manager,
            100,
            ActivityManager.RECENT_WITH_EXCLUDED
        ) as MutableList<ActivityManager.RecentTaskInfo?>?
    }

    fun getRunningTasks(context: Context): MutableList<ActivityManager.RunningTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningTasks(100)
    }

    fun getRunningTasksByReflect(context: Context): MutableList<ActivityManager.RunningTaskInfo?>? {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val clazz = Class.forName("android.app.ActivityManager")
        val method: Method = clazz.getDeclaredMethod("getRunningTasks", Int::class.java)
        method.isAccessible = true
        return method.invoke(manager, 100) as MutableList<ActivityManager.RunningTaskInfo?>?
    }

}