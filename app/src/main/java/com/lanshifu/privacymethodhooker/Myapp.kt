package com.lanshifu.privacymethodhooker

import android.app.Application
import android.content.Context
import android.util.Log
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
class Myapp : Application() {
    companion object {
        lateinit var context: Context

        var isAgreePrivacy = false
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        initPrivacyManager(this)
        super.onCreate()
        context = this

    }


    private fun initPrivacyManager(context: Context) {

        fun isAppCall(callerClassName: String): Boolean {
            return callerClassName.contains("tt") ||
                    callerClassName.contains("quwan")
        }

        PrivacyMethodManager.init(context, object : DefaultPrivacyMethodManagerDelegate() {

            override fun isDebugMode(): Boolean {
                return BuildConfig.DEBUG
            }

            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return isAgreePrivacy
            }

            override fun isUseCache(methodName: String, callerClassName: String): Boolean {

                //自定义是否要使用缓存，methodName可以在日志找到，过滤一下 onPrivacyMethodCall 关键字
                when (methodName) {
                    "TelephonyManager#getSimCountryIso",
                    "TelephonyManager#getSimOperator",
                    "TelephonyManager#getSimOperatorName",
                    "TelephonyManager#getNetworkOperator",
                    "TelephonyManager#getNetworkOperatorName",
                    "TelephonyManager#getNetworkCountryIso",
                    "TelephonyManager#getLine1Number",
                    "TelephonyManager#getCellLocation",

                    "LocationManager#getLastKnownLocation",

                    "PackageManager#getInstalledPackages",
                    "PackageManager#getInstalledApplications",

                    "ActivityManager#getRunningServices",
                    "ActivityManager#getRecentTasks",
                    "ActivityManager#getRunningTasks",
                    "ActivityManager#getRunningAppProcesses" -> return true

                    "ClipboardManager#getPrimaryClip" -> {
                        if (!isAppCall(callerClassName)) {
                            return true
                        }
                    }
                    else -> {

                    }
                }

                //app内的剪贴板，缓存，在customCacheExpireMap 中设置缓存时长
                if (callerClassName.contains("lanshifu") &&
                    methodName == "ClipboardManager#getPrimaryClip"
                ) {
                    return true
                }


                return super.isUseCache(methodName, callerClassName)

            }

            override fun customCacheExpireMap(): HashMap<String, Int> {
                return super.customCacheExpireMap().apply {
                    ///缓存过期时间(秒)
                    this["TelephonyManager#getSimCountryIso"] = 30
                    this["TelephonyManager#getSimOperator"] = 30
                    this["TelephonyManager#getSimOperatorName"] = 30
                    this["TelephonyManager#getNetworkOperator"] = 30
                    this["TelephonyManager#getNetworkOperatorName"] = 30
                    this["TelephonyManager#getNetworkCountryIso"] = 30
                    this["TelephonyManager#getLine1Number"] = 30
                    this["TelephonyManager#getCellLocation"] = 30

                    this["LocationManager#getLastKnownLocation"] = 30 //获取定位的频率

                    this["PackageManager#getInstalledPackages"] = 30
                    this["PackageManager#getInstalledApplications"] = 30

                    this["ActivityManager#getRunningAppProcesses"] = 30
                    this["ActivityManager#getRunningServices"] = 30
                    this["ActivityManager#getRecentTasks"] = 30
                    this["ActivityManager#getRunningTasks"] = 30

                    this["ClipboardManager#getPrimaryClip"] = 10 //限制剪贴板读取频率
                }
            }
        })
    }
}