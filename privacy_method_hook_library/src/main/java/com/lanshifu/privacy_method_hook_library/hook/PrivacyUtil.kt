package com.lanshifu.privacy_method_hook_library.hook

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
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
    callerClassName: String,
): CheckCacheAndPrivacyResult<T> {

    if (PrivacyMethodManager.getDelegate().isUseCache(key, callerClassName)) {
        // 缓存框架可以自定义的，保护一下
        try {
            val cache = PrivacyMethodCacheManager.get<T?>(key, callerClassName)
            if (cache != null) {
                PrivacyMethodManager.getDelegate()
                    .onPrivacyMethodCall(callerClassName, "$key  ->(return cache)", "")

                return CheckCacheAndPrivacyResult(cache, true)
            }
        } catch (e: Exception) {
            LogUtil.e("checkCacheAndPrivacy","e=${e.message}")
            e.printStackTrace()
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
        PrivacyMethodManager.getDelegate()
            .onPrivacyMethodCallIllegal(callerClassName, name, methodStack)
        return false
    }
    return true
}

fun <T> savePrivacyMethodResult(key: String, value: T, callerClassName: String): T {
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


fun checkReadPhoneStatePermission(key: String): Boolean {
    if (PrivacyMethodManager.mContext != null &&
        ActivityCompat.checkSelfPermission(
            PrivacyMethodManager.mContext!!,
            Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        LogUtil.i("PrivacyUtil", "$key, return null,no READ_PHONE_STATE permission")
        return false
    }
    return true
}