package com.lanshifu.privacymethodhooker

import android.app.Application
import android.content.Context
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.IPrivacyMethodCache
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacymethodhooker.mmkv.cache
import com.lanshifu.privacymethodhooker.mmkv.getMmkvValue
import com.lanshifu.privacymethodhooker.mmkv.putMmkvValue
import com.tencent.mmkv.MMKV

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
class Myapp : Application() {
    companion object {
        lateinit var context: Context

        var isAgreePrivacy = false
            get() {
                return getMmkvValue("isAgreePrivacy", false)
            }
            set(value) {
                field = value
                putMmkvValue("isAgreePrivacy", value)
            }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        MMKV.initialize(this)
        initPrivacyManager(this)
        super.onCreate()
        context = this

    }


    private fun initPrivacyManager(context: Context) {

        fun isAppCall(callerClassName: String): Boolean {
            return callerClassName.contains("lanshifu")
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

            override fun customCacheImpl(): IPrivacyMethodCache? {
                return MMKVCacheImpl()
            }
        })
    }
}

class MMKVCacheImpl : IPrivacyMethodCache {

    override fun <T> get(key: String): T? {
        return try {
            getMmkvValue(key, (null as T?)) as T
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override fun <T> put(key: String, value: T): T {
        putMmkvValue(key, value)
        return value
    }

    override fun remove(key: String) {
        cache.remove(key)
    }
}
