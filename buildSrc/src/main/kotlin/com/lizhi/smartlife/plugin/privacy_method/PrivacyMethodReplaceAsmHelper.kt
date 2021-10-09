package com.lizhi.smartlife.plugin.privacy_method

import com.lizhi.smartlife.plugin.base.AsmHelper
import com.lizhi.smartlife.plugin.base.Log.info
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

class PrivacyMethodReplaceAsmHelper : AsmHelper {

    private val asmItems = AnnotationParserAsmHelper.asmConfigs
    private val asmItemsClassMap = HashMap<String,String>()
    init {
        asmItems.forEach {
            asmItemsClassMap[it.targetClass] = it.targetClass
        }
        info("asmItemsMap size=${asmItemsClassMap.size}，asmItems.size=${asmItems.size}")
    }

    override fun modifyClass(srcClass: ByteArray?): ByteArray? {
        val classNode = ClassNode(Opcodes.ASM5)
        val classReader = ClassReader(srcClass)
        //1 将读入的字节转为classNode
        classReader.accept(classNode, 0)
        //2 对classNode的处理逻辑
        val methods = classNode.methods

        if (asmItemsClassMap.contains(classNode.name)){
            info("PrivacyMethodReplaceAsmHelper modifyClass ignore,classNode.name=${classNode.name}")
            return srcClass
        }

        methods.forEach { method ->
            method.instructions?.iterator()?.forEach { insnNode ->

                if (insnNode is MethodInsnNode) {

                    asmItems.forEach { asmItem ->
                        //INVOKEVIRTUAL android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List; ->
                        //INVOKESTATIC  com/lanshifu/asm_plugin_library/privacy/PrivacyUtil.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;
                        if (asmItem.oriDesc == insnNode.desc && asmItem.oriMethod == insnNode.name
                            && insnNode.opcode == asmItem.oriAccess &&
                            (insnNode.owner == asmItem.oriClass || asmItem.oriClass == "java/lang/Object")
                        ) {
                            info("PrivacyMethodReplaceAsmHelper hook: \n" +
                                    "opcode=${insnNode.opcode},owner=${insnNode.owner},desc=${insnNode.desc},name=${insnNode.name} ->\n"+
                                    "opcode=${asmItem.targetAccess},owner=${asmItem.targetClass},desc=${asmItem.targetDesc},name=${asmItem.targetMethod}")
                            insnNode.opcode = asmItem.targetAccess
                            insnNode.desc = asmItem.targetDesc
                            insnNode.owner = asmItem.targetClass
                            insnNode.name = asmItem.targetMethod
                        }
//java.lang.IncompatibleClassChangeError:
// The method 'java.util.List com.lanshifu.asm_plugin_library.privacy.PrivacyUtil.getRunningAppProcesses(android.app.ActivityManager)'
// was expected to be of type static but instead was found to be of type virtual (declaration of 'com.lanshifu.privacymethodhooker.test.TopLevelUtilKt'
                    }
                }
            }
        }


        val classWriter = ClassWriter(0)
        //3  将classNode转为字节数组
        classNode.accept(classWriter)
        return classWriter.toByteArray()
    }

}