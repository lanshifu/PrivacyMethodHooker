package com.lanshifu.lib.base.mmkv

import android.content.SharedPreferences
import android.util.Log
import com.tencent.mmkv.MMKV

/**
 * @author lanxiaobin
 * @date 2020-04-21
 *
 * MMKV 代理，统一打印日志，方便定位问题
 *
 * 支持多进程
 */
class MMKVDelegate {


    companion object {

        val TAG = "MMKVDelegate"

        private var mmkv: MMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)

        private val mmkvDelegate: MMKVDelegate by lazy { MMKVDelegate() }

        fun defaultMMKV(): MMKVDelegate {
            return mmkvDelegate
        }
    }


    fun getString(key: String, defValue: String?): String? {
        var value = mmkv.getString(key, defValue)
        logd(TAG, "getString,key=$key,value=$value")
        return value
    }

    fun putString(key: String, value: String?): SharedPreferences.Editor {
        logd(TAG, "putString,key=$key,value=$value")
        mmkv.putString(key, value)
        return mmkv
    }

    fun getInt(key: String, defValue: Int): Int {
        var value = mmkv.getInt(key, defValue)
        logd(TAG, "getInt,key=$key,value=$value")
        return value
    }

    fun putInt(key: String, value: Int): SharedPreferences.Editor {
        logd(TAG, "putInt,key=$key,value=$value")
        mmkv.putInt(key, value)
        return mmkv
    }

    fun getLong(key: String, defValue: Long): Long {
        var value = mmkv.getLong(key, defValue)
        logd(TAG, "getLong,key=$key,value=$value")
        return value
    }

    fun putLong(key: String, value: Long): SharedPreferences.Editor {
        logd(TAG, "putLong,key=$key,value=$value")
        mmkv.putLong(key, value)
        return mmkv
    }

    fun getFloat(key: String, defValue: Float): Float {
        var value = mmkv.decodeFloat(key, defValue)
        logd(TAG, "getFloat,key=$key,value=$value")
        return value
    }

    fun putFloat(key: String, value: Float): SharedPreferences.Editor {
        logd(TAG, "putFloat,key=$key,value=$value")
        mmkv.getFloat(key, value)
        return mmkv
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        var value = mmkv.getBoolean(key, defValue)
        logd(TAG, "getBoolean,key=$key,value=$value")
        return value
    }

    fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
        logd(TAG, "putBoolean,key=$key,value=$value")
        mmkv.putBoolean(key, value)
        return mmkv
    }

    fun getBytes(key: String, defValue: ByteArray?): ByteArray? {
        var value = mmkv.getBytes(key, defValue)
        logd(TAG, "getBytes,key=$key,value=$value")
        return value
    }

    fun putBytes(key: String, value: ByteArray): SharedPreferences.Editor {
        logd(TAG, "putBytes,key=$key,value=$value")
        mmkv.putBytes(key, value)
        return mmkv
    }

    fun remove(key: String): SharedPreferences.Editor {
        logd(TAG, "remove,key=$key")
        mmkv.remove(key)
        return mmkv
    }

    private fun logd(tag: String, text: String) {
        Log.d(tag, text)

    }
}