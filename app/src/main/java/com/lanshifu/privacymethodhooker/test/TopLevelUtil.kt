package com.lanshifu.privacymethodhooker.test

import android.app.ActivityManager
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
    for (process in manager.getRunningAppProcesses()) {
        if (process.pid == pid) {
            processName = process.processName
        }
    }
    return processName
}
