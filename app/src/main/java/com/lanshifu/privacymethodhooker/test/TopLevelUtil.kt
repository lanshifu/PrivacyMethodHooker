package com.lanshifu.privacymethodhooker.test

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

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

@RequiresApi(Build.VERSION_CODES.M)
fun getAllCellInfo(context: Activity): List<CellInfo>? {
    val manager: TelephonyManager =
        context.getSystemService(TelephonyManager::class.java) as TelephonyManager
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
        return null
    }
    return manager.getAllCellInfo()
}


@RequiresApi(Build.VERSION_CODES.M)
fun getDeviceId(context: Activity): String? {
    val manager: TelephonyManager =
        context.getSystemService(TelephonyManager::class.java) as TelephonyManager
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        context.requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE),0)
        return null
    }
    return manager.getDeviceId()
}