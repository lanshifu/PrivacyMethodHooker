package com.lanshifu.privacy_method_hook_library.cache

import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil


/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
object PrivacyMethodCacheManager : IPrivacyMethodCache {

    private const val CACHE_TIME_PERFIX = "CACHE_TIME_PERFIX_"
    private const val TAG = "PrivacyMethodCacheManager"

    private var mPrivacyMethodCacheImpl: IPrivacyMethodCache = DefaultPrivacyMethodCacheImpl()

    /**
     * 缓存过期时间配置
     */
    private val mCacheExpireTimeMap = HashMap<String, Int>()

    init {

        setCacheExpireTime(PrivacyMethodManager.getDelegate().customCacheExpireTime())

        PrivacyMethodManager.getDelegate().customCacheImpl()?.let {
            LogUtil.i(TAG, "customCacheImpl=$it")
            mPrivacyMethodCacheImpl = it
        }

    }

    /**
     * 设置缓存过期时间，单位s
     */
    private fun setCacheExpireTime(map: HashMap<String, Int>) {
        mCacheExpireTimeMap.putAll(map)
        LogUtil.i(TAG, "PrivacyMethodCache:setCacheExpireTime,map=$map")
    }

    /**
     * 设置缓存框架
     */
    fun setPrivacyMethodCacheImpl(iPrivacyMethodCache: IPrivacyMethodCache) {
        mPrivacyMethodCacheImpl = iPrivacyMethodCache
        LogUtil.d(
            TAG,
            "PrivacyMethodCache:setPrivacyMethodCache,name=${iPrivacyMethodCache.javaClass::getSimpleName}"
        )
    }

    fun <T> get(key: String, callClassName: String): T? {
        if (!PrivacyMethodManager.getDelegate().isUseCache(key, callClassName)) {
            return null
        }
        return get(key)
    }

    override fun <T> get(key: String): T? {
        checkCacheExpire(key)
        val get = mPrivacyMethodCacheImpl.get<T>(key)
        LogUtil.d(TAG, "get,key=$key,value=${get}")
        return get
    }


    override fun <T> put(key: String, value: T): T {
        LogUtil.d(TAG, "put,key=$key,value=$value")
        savePutTime(key)
        return mPrivacyMethodCacheImpl.put(key, value)
    }

    override fun remove(key: String) {
        LogUtil.d(TAG, "PrivacyMethodCache:remove,key=$key")
        mPrivacyMethodCacheImpl.remove(key)
    }

    /**
     * 每次put的时候记录时间
     */
    private fun savePutTime(key: String) {
        LogUtil.d(TAG, "PrivacyMethodCache:savePutTime,key=$key")
        val cacheMethodCallTimeKey = CACHE_TIME_PERFIX + key
        mPrivacyMethodCacheImpl.put(cacheMethodCallTimeKey, System.currentTimeMillis().toString())
    }

    /**
     * 每次get检查是否过期
     */
    private fun checkCacheExpire(key: String) {
        mCacheExpireTimeMap[key]?.let { cacheSaveSecond ->

            val cacheMethodCallTimeKey = CACHE_TIME_PERFIX + key
            //判断缓存是否过期，清除
            val prePutTime: Long =
                mPrivacyMethodCacheImpl.get<String>(cacheMethodCallTimeKey)?.toLong()
                    ?: System.currentTimeMillis()
            LogUtil.d(
                TAG,
                "PrivacyMethodCache:checkCacheExpire $key:${(System.currentTimeMillis() - prePutTime) / 1000} ->${cacheSaveSecond}"
            )
            if (System.currentTimeMillis() - prePutTime > cacheSaveSecond * 1000) {
                LogUtil.i(
                    TAG,
                    "PrivacyMethodCache:mCachePutTimeMillis.remove $key,cacheSaveSecond=$cacheSaveSecond"
                )
                // 清除缓存
                mPrivacyMethodCacheImpl.remove(key)
                PrivacyMethodManager.getDelegate().onCacheExpire(key)
            }

        }
    }
}