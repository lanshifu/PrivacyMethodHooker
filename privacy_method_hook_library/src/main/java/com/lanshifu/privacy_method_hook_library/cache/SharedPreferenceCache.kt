package com.lanshifu.privacy_method_hook_library.cache

import android.content.SharedPreferences
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/10/24
 *
 */
@Suppress("UNCHECKED_CAST")
class SharedPreferenceCacheImpl : IPrivacyMethodCache {

    private val sharedPreferences: SharedPreferences? =
        PrivacyMethodManager.mContext?.getSharedPreferences("privacy_method_cache", 0)

    override fun <T> get(key: String): T? {
        val value = sharedPreferences?.getString(key, null)
        LogUtil.d("SharedPreferenceCacheImpl", "get,key=$key,value=$value")
        if (value == null) {
            return null
        }

        return try {
            value as T
        } catch (e: Exception) {
            LogUtil.e("SharedPreferenceCacheImpl", "put,e=${e.message}")
            null
        }
    }

    override fun <T> put(key: String, value: T): T {
        //对字符串类型进行持久化（对象需要可序列化才行）
        if (value is String) {
            sharedPreferences?.apply {
                edit().putString(key, value.toString()).apply()
            }
            LogUtil.d("SharedPreferenceCacheImpl", "put,key=$key,value=$value")
        }

        return value
    }

    override fun remove(key: String) {
        sharedPreferences?.apply {
            edit().remove(key).apply()
        }
    }

}