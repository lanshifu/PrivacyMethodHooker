package com.lanshifu.privacymethodhooker

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.DefaultPrivacyMethodCacheImpl
import com.lanshifu.privacy_method_hook_library.cache.IPrivacyMethodCache
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.log.IPrivacyMethodLog
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/10/8
 */
object PrivacyMethodInitTask {

    fun init(context: Context) {

        /**
         * 通过类名区分是App内部调用还是三方SDK调用
         */
        fun isAppInnerCall(callerClassName: String): Boolean {
            return false
//            return callerClassName.contains("lanshifu")
        }

        PrivacyMethodManager.init(context, object : DefaultPrivacyMethodManagerDelegate() {

            override fun isDebugMode(): Boolean {
                // 内部版返回true
                return BuildConfig.DEBUG
            }

            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return MyApp.isAgreePrivacy
            }

            override fun isUseCache(methodName: String, callerClassName: String): Boolean {

                //自定义是否要使用缓存，methodName可以在日志找到，过滤一下 onPrivacyMethodCall 关键字
                when (methodName) {
                    "ClipboardManager#getPrimaryClip" -> {
                        if (!isAppInnerCall(callerClassName)) {
                            return true
                        }
                    }
                }

                return super.isUseCache(methodName, callerClassName)

            }

            override fun customLogImpl(): IPrivacyMethodLog {
                return CustomLogImpl()
            }

        })
    }
}

/**
 * 自定义日志输出
 */
class CustomLogImpl : IPrivacyMethodLog {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

}
