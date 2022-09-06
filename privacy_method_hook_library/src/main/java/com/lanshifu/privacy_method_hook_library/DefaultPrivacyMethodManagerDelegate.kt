package com.lanshifu.privacy_method_hook_library

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
class DefaultPrivacyMethodManagerDelegate : PrivacyMethodManagerDelegate {
    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isUseCache(): Boolean {
        return true
    }

    override fun showPrivacyMethodStack(): Boolean {
        return true
    }

    override fun onPrivacyMethodCall(methodName: String, methodStack: String) {
        Log.d(
            "PrivacyMethodManager",
            "onPrivacyMethodCall,methodName=$methodName,methodStack=$methodStack"
        )
    }

    override fun onPrivacyMethodCallIllegal(methodName: String, methodStack: String) {
        Log.e(
            "PrivacyMethodManager",
            "onPrivacyMethodCallIllegal,methodName=$methodName,methodStack=$methodStack"
        )
    }

}