package com.lanshifu.privacy_method_hook_library.log

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
interface IPrivacyMethodLog {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String)
}