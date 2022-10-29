package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.app.ActivityManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult


@Keep
object ActivityManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ActivityManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getRunningAppProcesses(
        packageManager: ActivityManager,
        callerClassName: String
    ): List<ActivityManager.RunningAppProcessInfo>? {
        val key = "ActivityManager#getRunningAppProcesses"
        val checkResult =
            checkCacheAndPrivacy<List<ActivityManager.RunningAppProcessInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.runningAppProcesses, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ActivityManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getRunningServices(
        packageManager: ActivityManager,
        maxNum: Int,
        callerClassName: String
    ): List<ActivityManager.RunningServiceInfo>? {
        val key = "ActivityManager#getRunningServices"
        val checkResult =
            checkCacheAndPrivacy<List<ActivityManager.RunningServiceInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.getRunningServices(maxNum), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ActivityManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getRecentTasks(
        packageManager: ActivityManager,
        maxNum: Int,
        flags: Int,
        callerClassName: String
    ): List<ActivityManager.RecentTaskInfo>? {
        val key = "ActivityManager#getRecentTasks"
        val checkResult =
            checkCacheAndPrivacy<List<ActivityManager.RecentTaskInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.getRecentTasks(maxNum, flags), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ActivityManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getRunningTasks(
        packageManager: ActivityManager,
        maxNum: Int,
        callerClassName: String
    ): List<ActivityManager.RunningTaskInfo>? {
        val key = "ActivityManager#getRunningTasks"
        val checkResult =
            checkCacheAndPrivacy<List<ActivityManager.RunningTaskInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.getRunningTasks(maxNum), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


}
