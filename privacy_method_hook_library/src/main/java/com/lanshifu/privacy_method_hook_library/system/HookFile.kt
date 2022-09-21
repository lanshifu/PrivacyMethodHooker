package com.lanshifu.privacy_method_hook_library.system

import com.lanshifu.privacy_method_hook_library.log.LogUtil
import java.io.File
import java.net.URI

/**
 * @author lanxiaobin
 * @date 2022/9/21
 *
 * todo：文件创建
 */
open class HookFile : File {
    constructor(pathname: String) : super(pathname) {
        if(pathname.contains("eth0") || pathname.contains("wlan0")){
            LogUtil.i("HookFile:pathname=$pathname")
        }
    }
    constructor(parent: String?, child: String) : super(parent, child) {

    }
    constructor(parent: File?, child: String) : super(parent, child) {

    }
    constructor(uri: URI) : super(uri) {

    }
}