package com.lanshifu.privacy_method_hook_library.hook.hookclass

import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmClassReplace
import com.lanshifu.privacy_method_hook_library.hook.checkAgreePrivacy
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

        //mac
        //sys/class/net/*/address
        val matchParttern = """^\/sys\/class\/net\/\w{1,}\/address"""
        val ismatchAddress = Regex(matchParttern).containsMatchIn(fileName)
        if (ismatchAddress) {
            LogUtil.d("读文件 fileName=$fileName")
            if (!checkAgreePrivacy("<init>", "java.io.File")) {

                // TODO: 改成空路径？
            }
        }

        if (fileName.startsWith("/system/build.prop")) {
            LogUtil.d("读文件 fileName=$fileName")
            if (!checkAgreePrivacy("<init>fileName=$fileName", "java.io.File")) {
                // TODO: 改成空路径？
            }
        }

    }

    constructor(parent: String?, child: String?) : super(parent, child) {}
    constructor(parent: File?, child: String?) : super(parent, child) {}
    constructor(uri: URI?) : super(uri) {}
}

@Keep
object ClassHook {

    /**
     * 替换 File(fileName:String)
     */
    @AsmClassReplace(oriClass = File::class, targetClass = HookFile::class)
    fun hookFile(fileName: String) {
        LogUtil.i("new HookFile")
    }

}