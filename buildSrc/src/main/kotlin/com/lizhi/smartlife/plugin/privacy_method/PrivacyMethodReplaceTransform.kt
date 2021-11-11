package com.lizhi.smartlife.plugin.privacy_method

import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
@Priority(100)
@AutoService(ClassTransformer::class)
class PrivacyMethodReplaceTransform : ClassTransformer {

    private val asmItems = AnnonationParserTransform.asmConfigs
    private val asmItemsClassMap = HashMap<String,String>()
    init {
        asmItems.forEach {
            asmItemsClassMap[it.targetClass] = it.targetClass
        }
        print("asmItemsMap size=${asmItemsClassMap.size}，asmItems.size=${asmItems.size}")
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {

        if (AnnonationParserTransform.asmConfigsMap.contains(klass.name)){
            print("PrivacyMethodReplaceAsmHelper modifyClass ignore,classNode.name=${klass.name}")
            return@also
        }

        klass.methods.forEach { method ->
            method.instructions?.iterator()?.forEach { insnNode ->

                if (insnNode is MethodInsnNode) {

                    asmItems.forEach { asmItem ->
                        //INVOKEVIRTUAL android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List; ->
                        //INVOKESTATIC  com/lanshifu/asm_plugin_library/privacy/PrivacyUtil.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;
                        if (asmItem.oriDesc == insnNode.desc && asmItem.oriMethod == insnNode.name
                            && insnNode.opcode == asmItem.oriAccess &&
                            (insnNode.owner == asmItem.oriClass || asmItem.oriClass == "java/lang/Object")
                        ) {

                            insnNode.opcode = asmItem.targetAccess
                            insnNode.desc = asmItem.targetDesc
                            insnNode.owner = asmItem.targetClass
                            insnNode.name = asmItem.targetMethod
                            print(
"""
PrivacyMethodReplaceAsmHelper hook:"
"opcode=${insnNode.opcode},owner=${insnNode.owner},desc=${insnNode.desc},name=${insnNode.name} ->
"opcode=${asmItem.targetAccess},owner=${asmItem.targetClass},desc=${asmItem.targetDesc},name=${asmItem.targetMethod}

"""
                            )
                        }}
                }
            }
        }
    }

}