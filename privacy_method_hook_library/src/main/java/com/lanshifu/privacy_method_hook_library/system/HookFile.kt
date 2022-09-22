package com.lanshifu.privacy_method_hook_library.system

import com.lanshifu.privacy_method_hook_library.log.LogUtil
import java.io.File
import java.net.URI

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
open class HookFile : File {
    constructor(fileName: String) : super(fileName) {
        LogUtil.i("HookFile:pathname=$fileName")
        when (fileName) {
            "/system/build.prop" -> {
                LogUtil.w("读文件 /system/build.prop")
            }

        }

        if (fileName.startsWith("/sys/class/net/")) {
            LogUtil.w("/sys/class/net/*")
        }

    }

    constructor(parent: String?, child: String?) : super(parent, child) {}
    constructor(parent: File?, child: String?) : super(parent, child) {}
    constructor(uri: URI?) : super(uri) {}
}