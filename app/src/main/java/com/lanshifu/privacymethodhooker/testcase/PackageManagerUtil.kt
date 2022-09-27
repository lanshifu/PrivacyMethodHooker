package com.lanshifu.privacymethodhooker.testcase

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
@RequiresApi(Build.VERSION_CODES.M)
object PackageManagerUtil {

    fun getInstalledPackages(context: Activity): List<PackageInfo>? {
        return context.packageManager?.getInstalledPackages(PackageManager.MATCH_ALL)
    }

    fun getInstalledApplications(context: Activity): List<ApplicationInfo>? {
        return context.packageManager?.getInstalledApplications(PackageManager.MATCH_ALL)
    }

}