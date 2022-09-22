package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy


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

        if (name.contains("/sys/class/net/") ||
            name.contains("pm list package") ||
            name.contains("getprop")
        ) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<String>(name, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                // todo 直接构造一个假的返回?
            }
            return runtime.exec(name)

        }
        return runtime.exec(name)
    }


}
