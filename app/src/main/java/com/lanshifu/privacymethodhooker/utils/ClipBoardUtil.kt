package com.lanshifu.privacymethodhooker.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.location.Location
import android.text.TextUtils
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/28
 */
object ClipBoardUtil {
    /**
     *
     * 获取剪切板内容
     *
     * @return
     */
    fun paste(context: Context): String? {
        val manager: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (manager != null) {
            if (manager.hasPrimaryClip() && (manager.getPrimaryClip()?.getItemCount() ?: 0) > 0) {
                val addedText: CharSequence = manager.getPrimaryClip()?.getItemAt(0)?.getText() ?: ""
                val addedTextString = addedText.toString()
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString
                }
            }
        }
        return ""
    }

    /**
     *
     * 获取剪切板内容
     *
     * @return
     */
    fun pasteByReflect(context: Context): String? {
        val manager: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (manager != null) {
            if (manager.hasPrimaryClip() && (manager.getPrimaryClip()?.getItemCount() ?: 0) > 0) {

                val clazz = Class.forName("android.content.ClipboardManager")
                val method: Method = clazz.getDeclaredMethod("getPrimaryClip")
                method.isAccessible = true
                val clipData = method.invoke(manager) as ClipData?

                val addedText: CharSequence = clipData?.getItemAt(0)?.getText() ?: ""
                val addedTextString = addedText.toString()
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString
                }
            }
        }
        return ""
    }
}