package com.lanshifu.privacymethodhooker.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.util.Log
import com.lanshifu.privacy_method_annotation.AsmField
import org.objectweb.asm.Opcodes

/**
 * @author lanxiaobin
 * @date 2021/10/9
 */
object PrivacyUtil {

    val TAG = "PrivacyUtil"

    var isAgreePrivacy = false

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(activityManager: ActivityManager): List<RunningAppProcessInfo?> {

        Log.i(TAG, "getRunningAppProcesses: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return activityManager.runningAppProcesses
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRecentTasks(
        activityManager: ActivityManager,
        maxNum: Int,
        flags: Int
    ): List<ActivityManager.RecentTaskInfo>? {

        Log.i(TAG, "getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return activityManager.getRecentTasks(maxNum, flags)
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRunningTasks(activityManager: ActivityManager,maxNum:Int): List<ActivityManager.RunningTaskInfo>? {

        Log.i(TAG, "getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return activityManager.getRunningTasks(maxNum)
        }

        return emptyList()
    }
}