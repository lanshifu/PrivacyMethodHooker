package com.lanshifu.privacy_method_hook_library.cache

import android.util.Log


/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
object PrivacyMethodCacheManager : IPrivacyMethodCache {

    private var mPrivacyMethodCache: IPrivacyMethodCache = DefaultPrivacyMethodCache()
    private var mCachePutTimeMillis = HashMap<String, Long>()

    private const val CACHE_TIME_PERFIX = "CACHE_TIME_PERFIX_"

    /**
     * 设置缓存框架
     */
    fun setPrivacyMethodCache(iPrivacyMethodCache: IPrivacyMethodCache) {
        mPrivacyMethodCache = iPrivacyMethodCache
    }


    /**
     * 支持缓存时间
     */
    fun <T> get(key: String, cacheSaveSeconds: Int): T? {


        //判断缓存是否过期，清除
        if (get<Long>(CACHE_TIME_PERFIX + key) != null &&
            (System.currentTimeMillis() - (get<Long>(CACHE_TIME_PERFIX + key)
                ?: 0L)) > cacheSaveSeconds * 1000
        ) {
            remove(CACHE_TIME_PERFIX + key)
            // todo 回调
            Log.d("PrivacyMethodCache", "mCachePutTimeMillis.remove $key")
        }

        return get(key)
    }

    override fun <T> get(key: String): T? {
        return mPrivacyMethodCache.get(key)
    }


    override fun put(key: String, value: Any) {
        // 记录时间
        mPrivacyMethodCache.put(CACHE_TIME_PERFIX + key, System.currentTimeMillis())

        return mPrivacyMethodCache.put(key, value)
    }

    override fun remove(key: String) {
        mPrivacyMethodCache.remove(key)
    }
}