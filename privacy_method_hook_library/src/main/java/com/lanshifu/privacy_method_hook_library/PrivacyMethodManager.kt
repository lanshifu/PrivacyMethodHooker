package com.lanshifu.privacy_method_hook_library

/**
 *
 * 隐私方法管理类
 * 提供给外部调用，设置代理
 */
object PrivacyMethodManager : IPrivacyMethodManager,PrivacyMethodManagerDelegate {

    const val TAG = "PrivacyMethodManager"

    private var mDelegate: PrivacyMethodManagerDelegate = DefaultPrivacyMethodManagerDelegate()

    override fun setDelegate(delegate: PrivacyMethodManagerDelegate) {
        mDelegate = delegate
    }

    override fun isAgreePrivacy(): Boolean {
        return mDelegate.isUseCache()
    }

    override fun isUseCache(): Boolean {
        return mDelegate.isUseCache()
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