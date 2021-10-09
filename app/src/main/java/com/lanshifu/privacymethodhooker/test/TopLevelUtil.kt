package com.lanshifu.privacymethodhooker.test

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Process

/**
 * @author lanxiaobin
 * @date 2020-04-25
 */

fun Context.getCurrentProcessName(): String? {
    val pid = Process.myPid()
    var processName = ""
    val manager: ActivityManager =
        applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = manager.runningAppProcesses
    for (process in runningAppProcesses) {

        if (process.pid == pid) {
            processName = process.processName
        }
    }
    return processName
}

fun getRunningAppProcesses(context: Context): List<RunningAppProcessInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses
}

fun getRecentTasks(context: Context): List<ActivityManager.RecentTaskInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRecentTasks(100,ActivityManager.RECENT_WITH_EXCLUDED)
}

fun getRunningTasks(context: Context): List<ActivityManager.RunningTaskInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRunningTasks(100)
}