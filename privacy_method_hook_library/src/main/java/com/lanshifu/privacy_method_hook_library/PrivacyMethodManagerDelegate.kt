package com.lanshifu.privacy_method_hook_library

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
interface PrivacyMethodManagerDelegate {

    /**
     * 是否同意隐私协议
     */
    fun isAgreePrivacy(): Boolean

    fun isUseCache(): Boolean

    /**
     * 是否显示隐私调用堆栈
     */
    fun showPrivacyMethodStack(): Boolean

    /**
     * 每一次隐私调用都会回调
     */
    fun onPrivacyMethodCall(methodName: String, methodStack: String)

    /**
     * 隐私调用违规的时候回调
     */
    fun onPrivacyMethodCallIllegal(methodName: String, methodStack: String)
}