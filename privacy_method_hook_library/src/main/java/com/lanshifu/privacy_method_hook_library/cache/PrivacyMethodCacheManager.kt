package com.lanshifu.privacy_method_hook_library.cache

import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil


/**
 * @author lanxiaobin
 * @date 2022/9/19
 */
object PrivacyMethodCacheManager : IPrivacyMethodCache {

    private const val CACHE_TIME_PERFIX = "CACHE_TIME_PERFIX_"

    private var mPrivacyMethodCacheImpl: IPrivacyMethodCache = DefaultPrivacyMethodCacheImpl()

    /**
     * 缓存过期时间配置
     */
    private val mCacheExpireTimeMap = HashMap<String, Int>()

    fun init() {

        setCacheExpireTime(PrivacyMethodManager.getDelegate().customCacheExpireMap())

        PrivacyMethodManager.getDelegate().customCacheImpl()?.let {
            LogUtil.i("customCacheImpl=$it")
            mPrivacyMethodCacheImpl = it
        }


        LogUtil.i("mCacheExpireTimeMap=$mCacheExpireTimeMap")
    }

    /**
     * 设置缓存过期时间，单位s
     */
    private fun setCacheExpireTime(map: HashMap<String, Int>) {
        mCacheExpireTimeMap.putAll(map)
        LogUtil.i("PrivacyMethodCache:setCacheExpireTime,map=$map")
    }

    /**
     * 设置缓存框架
     */
    fun setPrivacyMethodCacheImpl(iPrivacyMethodCache: IPrivacyMethodCache) {
        mPrivacyMethodCacheImpl = iPrivacyMethodCache
        LogUtil.d("PrivacyMethodCache:setPrivacyMethodCache,name=${iPrivacyMethodCache.javaClass::getSimpleName}")
    }

    fun <T> get(key: String, callClassName: String): T? {
        if (!PrivacyMethodManager.getDelegate().isUseCache(key, callClassName)) {
            return null
        }
        return get(key)
    }

    override fun <T> get(key: String): T? {
        checkCacheExpire(key)
        return mPrivacyMethodCacheImpl.get(key)
    }


    override fun <T> put(key: String, value: T): T {
        savePutTime(key)
        return mPrivacyMethodCacheImpl.put(key, value)
    }

    override fun remove(key: String) {
        LogUtil.d("PrivacyMethodCache:remove,key=$key")
        mPrivacyMethodCacheImpl.remove(key)
    }

    /**
     * 每次put的时候记录时间
     */
    private fun savePutTime(key: String) {
        LogUtil.d("PrivacyMethodCache:savePutTime,key=$key")
        val cacheMethodCallTimeKey = CACHE_TIME_PERFIX + key
        mPrivacyMethodCacheImpl.put(cacheMethodCallTimeKey, System.currentTimeMillis())
    }

    /**
     * 每次get检查是否过期
     */
    private fun checkCacheExpire(key: String) {
        mCacheExpireTimeMap[key]?.let { cacheSaveSecond ->

            val cacheMethodCallTimeKey = CACHE_TIME_PERFIX + key
            //判断缓存是否过期，清除
            val prePutTime = mPrivacyMethodCacheImpl.get<Long>(cacheMethodCallTimeKey)
                ?: System.currentTimeMillis()
            LogUtil.d("PrivacyMethodCache:checkCacheExpire:${(System.currentTimeMillis() - prePutTime)/1000} ->${cacheSaveSecond}")
            if (System.currentTimeMillis() - prePutTime > cacheSaveSecond * 1000) {
                LogUtil.i("PrivacyMethodCache:mCachePutTimeMillis.remove $key,cacheSaveSecond=$cacheSaveSecond")
                // 清除缓存
                mPrivacyMethodCacheImpl.remove(key)
                PrivacyMethodManager.getDelegate().onCacheExpire(key)
            }

        }
    }
}