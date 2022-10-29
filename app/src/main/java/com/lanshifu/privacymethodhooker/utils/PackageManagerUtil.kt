package com.lanshifu.privacymethodhooker.utils

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
@RequiresApi(Build.VERSION_CODES.M)
object PackageManagerUtil {

    fun getInstalledPackages(context: Activity): List<PackageInfo>? {
        return context.packageManager?.getInstalledPackages(PackageManager.MATCH_ALL)
    }

    fun getInstalledPackagesByReflect(context: Activity): List<PackageInfo>? {
        val clazz = Class.forName("android.content.pm.PackageManager")
        val method: Method = clazz.getDeclaredMethod("getInstalledPackages",Int::class.java)
        method.isAccessible = true
        return method.invoke(context.packageManager,PackageManager.MATCH_ALL) as List<PackageInfo>?
    }

    fun getInstalledApplications(context: Activity): List<ApplicationInfo>? {
        return context.packageManager?.getInstalledApplications(PackageManager.MATCH_ALL)
    }

    fun getInstalledApplicationsByReflect(context: Activity): List<ApplicationInfo>? {
        val clazz = Class.forName("android.content.pm.PackageManager")
        val method: Method = clazz.getDeclaredMethod("getInstalledApplications",Int::class.java)
        method.isAccessible = true
        return method.invoke(context.packageManager,PackageManager.MATCH_ALL) as List<ApplicationInfo>?
    }

}