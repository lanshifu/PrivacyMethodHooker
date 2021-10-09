package com.lanshifu.privacymethodhooker.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.location.Location
import android.location.LocationManager
import android.telephony.CellInfo
import android.telephony.TelephonyManager
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
    fun getRunningAppProcesses(manager: ActivityManager): List<RunningAppProcessInfo?> {

        Log.i(TAG, "getRunningAppProcesses: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return manager.runningAppProcesses
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRecentTasks(
        manager: ActivityManager,
        maxNum: Int,
        flags: Int
    ): List<ActivityManager.RecentTaskInfo>? {

        Log.i(TAG, "getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getRecentTasks(maxNum, flags)
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRunningTasks(manager: ActivityManager, maxNum:Int): List<ActivityManager.RunningTaskInfo>? {

        Log.i(TAG, "getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return manager.getRunningTasks(maxNum)
        }

        return emptyList()
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getAllCellInfo(manager: TelephonyManager): List<CellInfo>? {
        Log.i(TAG, "getAllCellInfo: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return manager.getAllCellInfo()
        }

        return emptyList()
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getDeviceId(manager: TelephonyManager): String? {
        Log.i(TAG, "getDeviceId: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return manager.getDeviceId()
        }

        return ""
    }

    /**
     * 读取位置信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = LocationManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getLastKnownLocation(manager: LocationManager, provider: String): Location? {
        Log.i(TAG, "getLastKnownLocation: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getLastKnownLocation(provider)
        }
        return null
    }

    /**
     * 读取位置信息
     */
//    @SuppressLint("MissingPermission")
//    @JvmStatic
//    @AsmField(oriClass = LocationManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
//    fun requestLocationUpdates(manager: LocationManager): Location? {
//        Log.i(TAG, "getLastKnownLocation: isAgreePrivacy=$isAgreePrivacy")
//        if (isAgreePrivacy) {
//            return manager.requestLocationUpdates(provider)
//        }
//        return null
//    }



}