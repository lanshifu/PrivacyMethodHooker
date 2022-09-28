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
        checkFileName(fileName)
    }

    constructor(parent: String?, child: String?) : super(parent, child) {}
    constructor(parent: File?, child: String?) : super(parent, child) {}
    constructor(uri: URI?) : super(uri) {}

    private fun checkFileName(fileName:String){
        LogUtil.d("HookFile:fileName=$fileName")
        //sys/class/net/*/address
        val pattern = """^\/sys\/class\/net\/\w+\/address"""
        val isMatchAddress = Regex(pattern).containsMatchIn(fileName)
        if (isMatchAddress) {
            LogUtil.d("读文件 fileName=$fileName")
            checkAgreePrivacy("<init>fileName=$fileName", "java.io.File")
        }

        if (fileName.startsWith("/system/build.prop")) {
            LogUtil.d("读文件 fileName=$fileName")
            checkAgreePrivacy("<init>fileName=$fileName", "java.io.File")
        }
    }
}

@Keep
object ClassHook {

    /**
     * new File(fileName:String) -> new HookFile(fileName:String)
     */
    @AsmClassReplace(oriClass = File::class, targetClass = HookFile::class)
    fun hookFile(fileName: String) {
        LogUtil.i("new HookFile")
    }

}