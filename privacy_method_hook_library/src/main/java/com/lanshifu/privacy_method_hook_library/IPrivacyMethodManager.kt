package com.lanshifu.privacy_method_hook_library

import com.lanshifu.privacy_method_hook_library.delegate.PrivacyMethodManagerDelegate

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
interface IPrivacyMethodManager {
    fun setDelegate(delegate: PrivacyMethodManagerDelegate)
}