package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.className
import com.lanshifu.plugin.extension.isRelease
import org.objectweb.asm.tree.ClassNode

/**
 * 提供拦截方法，可以统一过滤不需要hook的类
 */
open class AbsClassTransformer : ClassTransformer {


    fun onCommInterceptor(context: TransformContext, klass: ClassNode): Boolean {
//        "===onCommInterceptor--->$this====${klass.className}===".println()
        if (context.isRelease()) {
            return true
        }
//
//        if (!DoKitExtUtil.dokitPluginSwitchOpen()) {
//            return true
//        }
        //过滤kotlin module-info
        if (klass.className == "module-info") {
            return true
        }

        return false
    }
}