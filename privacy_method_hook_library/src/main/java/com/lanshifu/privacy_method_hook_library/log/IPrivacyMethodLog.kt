package com.lanshifu.privacy_method_hook_library.log

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
interface IPrivacyMethodLog {
    fun d(message: String)
    fun i(message: String)
    fun w(message: String)
    fun e(message: String)
}