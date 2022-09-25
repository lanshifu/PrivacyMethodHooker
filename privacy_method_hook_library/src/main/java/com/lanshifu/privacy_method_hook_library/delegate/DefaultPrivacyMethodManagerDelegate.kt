package com.lanshifu.privacy_method_hook_library.delegate

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
open class DefaultPrivacyMethodManagerDelegate : PrivacyMethodManagerDelegate {

    /**
     * 这些是默认不缓存的方法，黑名单
     */
    private val mCacheBlackMap = HashMap<String, String>()

    init {
        mCacheBlackMap["getSimSerialNumber"] = "1"
        mCacheBlackMap["getLine1Number"] = "1"
        mCacheBlackMap["getCellLocation"] = "1"
        mCacheBlackMap["getSimOperator"] = "1"
        mCacheBlackMap["getSimOperatorName"] = "1"
//        mCacheBlackMap["getSimCountryIso"] = "1"
        mCacheBlackMap["getNetworkOperator"] = "1"
        mCacheBlackMap["getNetworkOperatorName"] = "1"
        mCacheBlackMap["getNetworkCountryIso"] = "1"
        mCacheBlackMap["query"] = "1"
    }

    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isUseCache(methodName: String): Boolean {
        if (mCacheBlackMap.containsKey(methodName)) {
            return false
        }
        return true
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return false
    }

    override fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String) {
        Log.d(
            "PrivacyMethodManager",
            "onPrivacyMethodCall,className=$className,methodName=$methodName,methodStack=$methodStack"
        )
    }

    override fun onPrivacyMethodCallIllegal(
        className: String,
        methodName: String,
        methodStack: String
    ) {
        Log.e(
            "PrivacyMethodManager",
            "onPrivacyMethodCallIllegal,className=$className，methodName=$methodName,methodStack=$methodStack"
        )
    }

    override fun onCacheExpire(methodName: String) {
        Log.d(
            "PrivacyMethodManager",
            "onCacheExpire,methodName=$methodName"
        )
        //todo toast
    }

    override fun customCacheExpireMap(): HashMap<String, Int> {
        return HashMap<String, Int>().apply {

            ///缓存60s过期
            this["getSimCountryIso"] = 10
        }
    }

}