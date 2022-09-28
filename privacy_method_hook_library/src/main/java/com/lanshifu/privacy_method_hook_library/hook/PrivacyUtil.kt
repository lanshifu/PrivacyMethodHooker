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

    if (PrivacyMethodManager.getDelegate().isUseCache(key, callerClassName)) {
        val cache = PrivacyMethodCacheManager.get<T?>(key, callerClassName)
        if (cache != null) {
            return CheckCacheAndPrivacyResult(cache, true)
        }
    }

    val isAgreePrivacy = checkAgreePrivacy(key, callerClassName)
    return CheckCacheAndPrivacyResult(null, isAgreePrivacy)
}

fun checkAgreePrivacy(name: String, callerClassName: String = ""): Boolean {
    var methodStack = ""
    if (PrivacyMethodManager.getDelegate().isShowPrivacyMethodStack() ||
        !PrivacyMethodManager.getDelegate().isAgreePrivacy()
    ) {
        methodStack = Log.getStackTraceString(Throwable())
    }

    PrivacyMethodManager.getDelegate().onPrivacyMethodCall(callerClassName, name, methodStack)

    if (!PrivacyMethodManager.getDelegate().isAgreePrivacy()) {
        PrivacyMethodManager.getDelegate().onPrivacyMethodCallIllegal(callerClassName, name, methodStack)
        return false
    }
    return true
}

fun <T> saveResult(key: String, value: T, callerClassName: String): T {
    LogUtil.d("saveResult,key=$key,callerClassName=${callerClassName}")
    if (value != null && PrivacyMethodManager.getDelegate().isUseCache(key, callerClassName)) {
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