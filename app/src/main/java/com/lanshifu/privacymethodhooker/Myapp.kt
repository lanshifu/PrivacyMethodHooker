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
    companion object{
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
                    "LocationManager#getLastKnownLocation" -> return true
                    "getLine1Number" -> return true
                }

                //app内的剪贴板，缓存，在customCacheExpireMap 中设置缓存时长
                if (callerClassName.contains("lanshifu") &&
                    methodName == "ClipboardManager#getPrimaryClip") {
                    return true
                }


                return super.isUseCache(methodName, callerClassName)

            }

            override fun customCacheExpireMap(): HashMap<String, Int> {
                return super.customCacheExpireMap().apply {
                    ///缓存过期时间
                    this["TelephonyManager#getSimCountryIso"] = 10
                    this["Settings\$System#getString(android_id)"] = 10
                    this["Settings\$Secure#getString(bluetooth_address)"] = 10
                    this["Settings\$Secure#getString(bluetooth_name)"] = 10

                    this["ClipboardManager#getPrimaryClip"] = 20 //限制剪贴板读取频率
                    this["LocationManager#getLastKnownLocation"] = 30 //获取定位的频率
                }
            }
        })
    }
}