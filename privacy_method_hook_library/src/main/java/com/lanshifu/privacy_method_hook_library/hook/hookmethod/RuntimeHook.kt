package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object RuntimeHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        name: String,
        callerClassName: String
    ): Process {

        val key = "Runtime#exec($name)"
        if (name.contains("/sys/class/net/") ||
            name.contains("pm list package") ||
            name.contains("getprop")
        ) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
//                if(checkCacheAndPrivacy.cacheData != null){
//                }
                // todo 直接构造一个假的返回?
                return runtime.exec("pwd")
            }
            return runtime.exec(name)

        }
        return saveResult(name, runtime.exec(name), callerClassName)
    }


}
