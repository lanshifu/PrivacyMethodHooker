package com.lanshifu.privacy_method_hook_library.cache

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
class DefaultPrivacyMethodCache : IPrivacyMethodCache {

    private val mCache = mutableMapOf<String, Any>()


    override fun <T> get(key: String): T? {
        return mCache[key] as T
    }


    override fun put(key: String, value: Any) {
        mCache[key] = value
    }

    override fun remove(key: String) {
        mCache.remove(key)
    }
}