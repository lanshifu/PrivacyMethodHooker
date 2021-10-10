package com.lanshifu.privacymethodhooker.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.ContentResolver
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.Keep
import com.lanshifu.privacy_method_annotation.AsmClass
import com.lanshifu.privacy_method_annotation.AsmField
import com.lanshifu.privacy_method_annotation.AsmMethodOpcodes

/**
 * @author lanxiaobin
 * @date 2021/10/9
 *
 * 不要被混淆
 *
 * Kotlin类必须要JvmStatic
 */
@Keep
@AsmClass
object PrivacyUtil {

    val TAG = "PrivacyUtil"

    var isAgreePrivacy = false

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(manager: ActivityManager): List<RunningAppProcessInfo?> {

        Log.i(TAG, "getRunningAppProcesses: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy){
            return manager.runningAppProcesses
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
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
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
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
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
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
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
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
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmField(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
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
    @JvmStatic
    @AsmField(oriClass = Settings.System::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(resolver: ContentResolver,  name:String): String? {
//        Settings.System.getString(context.getContentResolver(),  Settings.Secure.ANDROID_ID)
//    INVOKESTATIC android/provider/Settings$System.getString (Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;
        Log.i(TAG, "Settings.System.getString,name=$name,isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return Settings.System.getString(resolver,name)
        }
        return null
    }

    /**
     * 读取位置信息
     */
//    @SuppressLint("MissingPermission")
//    @JvmStatic
//    @AsmField(oriClass = LocationManager::class, oriAccess = AsmOpcodes.INVOKEVIRTUAL)
//    fun requestLocationUpdates(manager: LocationManager): Location? {
//        Log.i(TAG, "getLastKnownLocation: isAgreePrivacy=$isAgreePrivacy")
//        if (isAgreePrivacy) {
//            return manager.requestLocationUpdates(provider)
//        }
//        return null
//    }



}