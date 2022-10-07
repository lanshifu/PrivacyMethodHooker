package com.lanshifu.privacymethodhooker.mmkv

import com.google.gson.Gson
import com.lanshifu.lib.base.mmkv.MMKVDelegate
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * mmkv封装一下，任何地方都可以调用 putMmkvValue 和  getMmkvValue，支持对象序列化
 */

val cache = MMKVDelegate.defaultMMKV()
val gson = Gson()

fun <T> Any.putMmkvValue(key: String, value: T) = cache.run {
    when (value) {
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        is ByteArray -> putBytes(key, value)
        else -> putString(key, serialize(value))
    }
}


fun <T> Any.getMmkvValue(key: String, default: T?): T = cache.run {
    val result = when (default) {
        is Long -> getLong(key, default)
        is String -> getString(key, default)
        is Int -> getInt(key, default)
        is Boolean -> getBoolean(key, default)
        is Float -> getFloat(key, default)
        is ByteArray -> getBytes(key, default)
        else -> {
            val str = getString(key, serialize(default))
            if(str == null){
                default
            } else{
                deSerialization(str)
            }

        }
    }
    result as T
}


private fun <T> serialize(obj: T?): String {
    return gson.toJson(obj)
}

private inline fun <reified T> deSerialization(str: String?): T? {
    LogUtil.d("deSerialization,str=$str")

    return gson.fromJson(str, T::class.java)
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)