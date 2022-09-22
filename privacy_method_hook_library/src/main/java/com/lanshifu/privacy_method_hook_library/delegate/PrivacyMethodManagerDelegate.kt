package com.lanshifu.privacy_method_hook_library.delegate

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
     * @param methodName: 对应某个隐私API方法名
     */
    fun isUseCache(methodName: String): Boolean

    /**
     * 是否显示隐私方法调用堆栈，
     * release包建议关闭
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


    /**
     * 自定义缓存过期时间
     */
    fun customCacheExpireMap(): Map<String, Int>
}