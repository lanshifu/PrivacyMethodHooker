package com.lanshifu.privacy_method_hook_library.delegate

import com.lanshifu.privacy_method_hook_library.cache.IPrivacyMethodCache
import com.lanshifu.privacy_method_hook_library.log.IPrivacyMethodLog

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
interface IPrivacyMethodManagerDelegate {

    /**
     * 是否同意隐私协议
     */
    fun isAgreePrivacy(): Boolean

    /**
     * debug 模式会弹toast
     */
    fun isDebugMode(): Boolean

    /**
     * 是否使用缓存
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun isUseCache(methodName: String, callerClassName: String): Boolean

    /**
     * 是否显示隐私方法调用堆栈，默认情况下只有违规调用才会打印堆栈
     *
     */
    fun isShowPrivacyMethodStack(): Boolean

    /**
     * 每一次隐私调用都会回调
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun onPrivacyMethodCall(callerClassName: String, methodName: String, methodStack: String)

    /**
     * 隐私调用违规的时候回调
     * @param methodName: 对应某个隐私API方法名
     * @param callerClassName: 调用者类名
     */
    fun onPrivacyMethodCallIllegal(callerClassName: String, methodName: String, methodStack: String)

    /**
     * 缓存过期回调
     * @param methodName: 对应某个隐私API方法名
     */
    fun onCacheExpire(methodName: String)

    /**
     * 自定义缓存过期时间，key是方法名，value是多少秒
     */
    fun customCacheExpireTime(): HashMap<String, Int>

    /**
     * 自定义缓存框架，默认是HashMap
     */
    fun customCacheImpl(): IPrivacyMethodCache?

    /**
     * 自定义日志输出
     */
    fun customLogImpl(): IPrivacyMethodLog?

    /**
     * 使用磁盘缓存，重新打开App不会重复去读隐私API
     */
    fun isUseDiskCache(): Boolean
}