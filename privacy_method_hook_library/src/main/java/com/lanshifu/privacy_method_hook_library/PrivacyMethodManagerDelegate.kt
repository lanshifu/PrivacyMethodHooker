package com.lanshifu.privacy_method_hook_library

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
interface PrivacyMethodManagerDelegate {

    /**
     * 是否同意隐私协议
     */
    fun isAgreePrivacy(): Boolean

    /**
     * 是否使用缓存
     */
    fun isUseCache(): Boolean

    /**
     * 是否显示隐私调用堆栈
     */
    fun isShowPrivacyMethodStack(): Boolean

    /**
     * 每一次隐私调用都会回调
     */
    fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String)

    /**
     * 隐私调用违规的时候回调
     */
    fun onPrivacyMethodCallIllegal(className: String, methodName: String, methodStack: String)

    /**
     * 缓存过期回调
     */
    fun onCacheExpire(methodName: String)
}