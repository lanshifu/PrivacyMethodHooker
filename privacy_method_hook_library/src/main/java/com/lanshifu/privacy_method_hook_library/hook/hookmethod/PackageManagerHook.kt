package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult


@Keep
object PackageManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = PackageManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getInstalledPackages(
        packageManager: PackageManager,
        flags: Int,
        callerClassName: String
    ): List<PackageInfo>? {
        val key = "PackageManager#getInstalledPackages"
        val checkResult = checkCacheAndPrivacy<List<PackageInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.getInstalledPackages(flags), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = PackageManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getInstalledApplications(
        packageManager: PackageManager,
        flags: Int,
        callerClassName: String
    ): List<ApplicationInfo>? {
        val key = "PackageManager#getInstalledApplications"
        val checkResult = checkCacheAndPrivacy<List<ApplicationInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            savePrivacyMethodResult(key, packageManager.getInstalledApplications(flags), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}
