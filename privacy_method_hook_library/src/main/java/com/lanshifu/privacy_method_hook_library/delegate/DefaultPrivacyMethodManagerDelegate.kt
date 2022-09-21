package com.lanshifu.privacy_method_hook_library.delegate

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
open class DefaultPrivacyMethodManagerDelegate : PrivacyMethodManagerDelegate {

    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isUseCache(methodName: String): Boolean {
        // todo 缓存配置
        return true
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return true
    }

    override fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String) {
        Log.d(
            "PrivacyMethodManager",
            "onPrivacyMethodCall,methodName=$methodName,methodStack=$methodStack"
        )
    }

    override fun onPrivacyMethodCallIllegal(
        className: String,
        methodName: String,
        methodStack: String){

        Log.e(
            "PrivacyMethodManager",
            "onPrivacyMethodCallIllegal,methodName=$methodName,methodStack=$methodStack"
        )
    }

    override fun onCacheExpire(methodName: String) {
        Log.d(
            "PrivacyMethodManager",
            "onCacheExpire,methodName=$methodName"
        )
    }

}