package com.lizhi.plugin.privacy_method

import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.File
import java.io.PrintWriter


/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class PrivacyMethodReplaceTransform : ClassTransformer {
    private lateinit var logger: PrintWriter
    private val asmItems = AnnonationParserTransform.asmConfigs
    private var asmItemsClassMap: HashMap<String, String> = HashMap<String, String>()

    companion object{
    }


    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("PrivacyMethodReplaceTransform").file(context.name)
            .file("report.txt").touch().printWriter()
        logger.println("\n --start-- ${System.currentTimeMillis()}")

        AnnonationParserTransform.asmConfigs.forEach {
            asmItemsClassMap!![it.targetClass] = it.targetClass
        }
        logger.print("PrivacyMethodReplaceAsmHelper asmItemsMap size=${asmItemsClassMap!!.size}，asmItems.size=${asmItems.size}")


    }

    override fun onPostTransform(context: TransformContext) {
        logger.println("\n --end-- ${System.currentTimeMillis()}")
        this.logger.close()
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {

        if (AnnonationParserTransform.asmConfigsMap.contains(klass.name)) {
            logger.print("PrivacyMethodReplaceAsmHelper modifyClass ignore,classNode.name=${klass.name}")
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

                            logger.print(
                                "\n\rPrivacyMethodReplaceAsmHelper hook:\n" +
                                        "opcode=${insnNode.opcode},owner=${insnNode.owner},desc=${insnNode.desc},name=${insnNode.name} ->\n" +
                                        "opcode=${asmItem.targetAccess},owner=${asmItem.targetClass},desc=${asmItem.targetDesc},name=${asmItem.targetMethod}\n\r"
                            )
                            insnNode.opcode = asmItem.targetAccess
                            insnNode.desc = asmItem.targetDesc
                            insnNode.owner = asmItem.targetClass
                            insnNode.name = asmItem.targetMethod


                        }
                    }
                }
            }
        }
    }

    fun getClassFilePath(clazz: Class<*>): String {
        // file:/Users/zhy/hongyang/repo/BlogDemo/app/build/intermediates/javac/debug/classes/
        val buildDir = clazz.protectionDomain.codeSource.location.file
        val fileName = clazz.simpleName + ".class"
        val file =
            File(buildDir + clazz.getPackage().name.replace("[.]".toRegex(), "/") + "/", fileName)
        return file.getAbsolutePath()
    }

}