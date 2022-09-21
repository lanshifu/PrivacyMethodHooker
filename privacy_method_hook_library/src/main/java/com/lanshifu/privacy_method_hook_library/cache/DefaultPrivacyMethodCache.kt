package com.lanshifu.privacy_method_hook_library.cache

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
@Suppress("UNCHECKED_CAST")
class DefaultPrivacyMethodCache : IPrivacyMethodCache {

    private val mCache = mutableMapOf<String, Any>()

    override fun <T> get(key: String): T? {
        return try {
            mCache[key] as T
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override fun put(key: String, value: Any) {
        mCache[key] = value
    }

    override fun remove(key: String) {
        mCache.remove(key)
    }
}