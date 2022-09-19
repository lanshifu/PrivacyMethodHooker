package com.lanshifu.privacy_method_hook_library.cache

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
interface IPrivacyMethodCache {

    fun <T> get(key: String): T?

    fun put(key: String, value: Any)

    fun remove(key: String)

}