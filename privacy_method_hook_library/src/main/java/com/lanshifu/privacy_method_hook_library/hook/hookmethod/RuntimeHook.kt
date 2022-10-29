package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult
import java.io.File
import java.io.IOException


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
        command: String,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($command)"
        if (checkHook(command)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                // 模拟exec抛IOException
                throw IOException("隐私同意前调用")
            }
            return runtime.exec(command)

        }
        return savePrivacyMethodResult(key, runtime.exec(command), callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        commands: Array<String>,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($commands)"
        if (checkHook(commands)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                return null
            }
            // 模拟exec抛IOException
            throw IOException("隐私同意前调用")

        }
        return savePrivacyMethodResult(key, runtime.exec(commands), callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        command: String,
        envp: Array<String>,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($command)"
        if (checkHook(command)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                return null
            }
            // 模拟exec抛IOException
            throw IOException("隐私同意前调用")

        }
        return savePrivacyMethodResult(key, runtime.exec(command, envp), callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        command: String,
        envp: Array<String>,
        file: File,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($command)"
        if (checkHook(command)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                return null
            }
            // 模拟exec抛IOException
            throw IOException("隐私同意前调用")

        }
        return savePrivacyMethodResult(key, runtime.exec(command, envp, file), callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        cmdarray: Array<String>,
        envp: Array<String>,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($cmdarray)"
        if (checkHook(cmdarray)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                return null
            }
            // 模拟exec抛IOException
            throw IOException("隐私同意前调用")

        }
        return savePrivacyMethodResult(key, runtime.exec(cmdarray, envp), callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = Runtime::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun exec(
        runtime: Runtime,
        cmdarray: Array<String>,
        envp: Array<String>,
        file: File,
        callerClassName: String
    ): Process? {

        val key = "Runtime#exec($cmdarray)"
        if (checkHook(cmdarray)) {
            val checkCacheAndPrivacy = checkCacheAndPrivacy<Process>(key, callerClassName)
            if (checkCacheAndPrivacy.shouldReturn()) {
                return null
            }
            // 模拟exec抛IOException
            throw IOException("隐私同意前调用")

        }
        return savePrivacyMethodResult(key, runtime.exec(cmdarray, envp, file), callerClassName)
    }


    private fun checkHook(name: String): Boolean {
        return name.contains("/sys/class/net/") ||
                name.contains("pm list package") ||
                name.contains("ps") ||
                name.contains("getprop")
    }

    private fun checkHook(names: Array<String>): Boolean {
        for (name in names) {
            return name.contains("/sys/class/net/") ||
                    name.contains("pm list package") ||
                    name.contains("ps") ||
                    name.contains("getprop")
        }
        return false;

    }
}
