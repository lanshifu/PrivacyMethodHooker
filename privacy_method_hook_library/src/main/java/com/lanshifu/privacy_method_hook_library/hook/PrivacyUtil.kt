package com.lanshifu.privacy_method_hook_library.hook

import android.util.Log
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.PrivacyMethodCacheManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/21
 * 检查缓存和隐私
 */
fun <T> checkCacheAndPrivacy(
    key: String,
    callerClassName: String
): CheckCacheAndPrivacyResult<T> {

    if (PrivacyMethodManager.isUseCache(key, callerClassName)) {
        val cache = PrivacyMethodCacheManager.get<T?>(key)
        if (cache != null) {
            return CheckCacheAndPrivacyResult(cache, true)
        }
    }

    val isAgreePrivacy = checkAgreePrivacy(key, callerClassName)
    return CheckCacheAndPrivacyResult(null, isAgreePrivacy)
}

fun checkAgreePrivacy(name: String, callerClassName: String = ""): Boolean {
    var methodStack = ""
    if (PrivacyMethodManager.isShowPrivacyMethodStack()) {
        methodStack = Log.getStackTraceString(Throwable())
    }

    PrivacyMethodManager.onPrivacyMethodCall(callerClassName, name, methodStack)

    if (!PrivacyMethodManager.isAgreePrivacy()) {
        PrivacyMethodManager.onPrivacyMethodCallIllegal(callerClassName, name, methodStack)
        return false
    }
    return true
}

fun <T> saveResult(key: String, value: T, callerClassName: String): T {
    LogUtil.d("saveResult,key=$key,value=$value,callerClassName=${callerClassName}")
    if (value != null) {
        PrivacyMethodCacheManager.put(key, value as Any)
    }
    return value
}

class CheckCacheAndPrivacyResult<T>(
    var cacheData: T?,
    var isAgreePrivacy: Boolean,
) {
    fun shouldReturn(): Boolean {
        return cacheData != null || !isAgreePrivacy
    }
}