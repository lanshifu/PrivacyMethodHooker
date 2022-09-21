package com.lanshifu.privacy_method_hook_library

import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.delegate.PrivacyMethodManagerDelegate

/**
 *
 * 隐私方法管理类
 * 提供给外部调用，设置代理
 */
object PrivacyMethodManager : IPrivacyMethodManager, PrivacyMethodManagerDelegate {

    const val TAG = "PrivacyMethodManager"

    private var mDelegate: PrivacyMethodManagerDelegate = DefaultPrivacyMethodManagerDelegate()

    fun getInstance(): IPrivacyMethodManager {
        return this
    }

    override fun setDelegate(delegate: PrivacyMethodManagerDelegate) {
        mDelegate = delegate
    }

    override fun isAgreePrivacy(): Boolean {
        return mDelegate.isAgreePrivacy()
    }

    override fun isUseCache(methodName: String): Boolean {
        return mDelegate.isUseCache(methodName)
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return mDelegate.isShowPrivacyMethodStack()
    }

    override fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String) {
        mDelegate.onPrivacyMethodCall(className, methodName, methodStack)
    }

    override fun onPrivacyMethodCallIllegal(
        className: String,
        methodName: String,
        methodStack: String
    ) {
        mDelegate.onPrivacyMethodCallIllegal(className, methodName, methodStack)
    }

    override fun onCacheExpire(methodName: String) {
        mDelegate.onCacheExpire(methodName)
    }

}