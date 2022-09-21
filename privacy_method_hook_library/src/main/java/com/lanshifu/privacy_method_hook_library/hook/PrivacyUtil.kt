package com.lanshifu.privacy_method_hook_library.hook

import android.util.Log
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.PrivacyMethodCacheManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
fun checkAgreePrivacy(name: String, className: String = ""): Boolean {

    var methodStack = ""
    if (PrivacyMethodManager.isShowPrivacyMethodStack()) {
        methodStack = Log.getStackTraceString(Throwable())
    }

    PrivacyMethodManager.onPrivacyMethodCall(className, name, methodStack)

    if (!PrivacyMethodManager.isAgreePrivacy()) {
        //没有同意隐私权限，打印堆栈，toast
        PrivacyMethodManager.onPrivacyMethodCallIllegal(className, name, methodStack)
        return false
    }
    return true
}

fun saveResult(key: String, value: String, callerClassName: String) {

    LogUtil.d("saveResult,key=$key,value=$value,callerClassName=${callerClassName}")
    // todo 区分app和第三方调用，缓存处理

    PrivacyMethodCacheManager.put(key, value)
}