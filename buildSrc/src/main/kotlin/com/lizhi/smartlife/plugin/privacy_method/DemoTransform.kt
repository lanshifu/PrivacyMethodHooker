package com.lizhi.smartlife.plugin.privacy_method

import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.kotlinx.ifNotEmpty
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.className
import com.google.auto.service.AutoService
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class DemoTransform : ClassTransformer {

    companion object{
        private const val AsmFieldDesc = "Lcom/lanshifu/privacy_method_annotation/AsmField;"
        var asmConfigs = mutableListOf<AsmItem>()
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {
        klass.methods.forEach { method ->
            method.instructions?.iterator()?.forEach { insnNode ->

                if (insnNode is MethodInsnNode) {

                    //命中方法
                    if (insnNode.desc  == "android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List;" &&
                        insnNode.name == "getRunningAppProcesses" &&
                        insnNode.opcode == Opcodes.INVOKESTATIC
                    ) {
                        //方法指令替换
                        insnNode.opcode = Opcodes.INVOKESTATIC
                        //调用类替换
                        insnNode.owner = "com/lanshifu/asm_plugin_library/privacy/PrivacyUtil"
                        //方法名替换
                        insnNode.name = "getRunningAppProcesses"
                        //参数替换
                        insnNode.desc = "com/lanshifu/asm_plugin_library/privacy/PrivacyUtil.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;"

                    }
                }
            }
        }
    }

}