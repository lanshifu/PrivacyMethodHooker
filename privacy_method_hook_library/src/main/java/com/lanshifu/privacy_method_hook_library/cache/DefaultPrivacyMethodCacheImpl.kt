package com.lanshifu.privacy_method_hook_library.cache

import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager

/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
@Suppress("UNCHECKED_CAST")
open class DefaultPrivacyMethodCacheImpl : IPrivacyMethodCache {

    private val mCache = mutableMapOf<String, Any>()

    private val mDiskCache: SharedPreferenceCacheImpl = SharedPreferenceCacheImpl()

    override fun <T> get(key: String): T? {
        try {
            if (mCache[key] != null) {
                return mCache[key] as T
            }
            if (PrivacyMethodManager.getDelegate().isUseDiskCache()) {
                val getFromSp = mDiskCache.get<T>(key)
                if (getFromSp != null) {
                    return getFromSp
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


    override fun <T> put(key: String, value: T): T {
        value?.let {
            mCache[key] = value
            if (PrivacyMethodManager.getDelegate().isUseDiskCache()) {
                //默认只对字符串类型进行持久化（对象需要可序列化）
                if (value is String) {
                    mDiskCache.put(key, value)
                }
            }
        }
        return value
    }

    override fun remove(key: String) {
        mCache.remove(key)
        if (PrivacyMethodManager.getDelegate().isUseDiskCache()) {
            mDiskCache.remove(key)
        }
    }
}