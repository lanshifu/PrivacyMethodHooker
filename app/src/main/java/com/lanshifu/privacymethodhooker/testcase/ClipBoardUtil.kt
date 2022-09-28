package com.lanshifu.privacymethodhooker.testcase

import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils

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
}