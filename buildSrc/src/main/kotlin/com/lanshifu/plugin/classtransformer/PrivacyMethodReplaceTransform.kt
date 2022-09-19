package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.PrintWriter


/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class PrivacyMethodReplaceTransform : AbsClassTransformer() {
    private lateinit var logger: PrintWriter
    private val asmItems = AnnotationParserClassTransform.asmConfigs
    private var asmItemsClassMap: HashMap<String, String> = HashMap<String, String>()

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("PrivacyMethodReplaceTransform").file(context.name)
            .file("report.txt").touch().printWriter()
        logger.println("--start-- ${System.currentTimeMillis()}")

        AnnotationParserClassTransform.asmConfigs.forEach {
            asmItemsClassMap[it.targetClass] = it.targetClass
        }
        logger.print("\nasmItemsMap size=${asmItemsClassMap.size}，asmItems.size=${asmItems.size}\n\n")
        logger.print("asmItemsClassMap=${asmItemsClassMap} \n\n")
        logger.print("asmItems=${asmItems} \n\n")
    }

    override fun onPostTransform(context: TransformContext) {
        logger.println("\n --end-- ${System.currentTimeMillis()}")
        this.logger.close()
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {

        if (onCommInterceptor(context, klass)) {
            return klass
        }

        if (AnnotationParserClassTransform.asmConfigsMap.contains(klass.name)) {
            logger.print("\nPrivacyMethodReplaceAsmHelper modifyClass ignore,classNode.name=${klass.name}\n")
            return@also
        }

        klass.methods.forEach { method ->
            method.instructions?.iterator()?.forEach { insnNode ->

                if (insnNode is MethodInsnNode) {

                    asmItems.forEach { asmItem ->
                        //INVOKEVIRTUAL android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List; ->
                        //INVOKESTATIC  com/lanshifu/asm_plugin_library/privacy/PrivacyUtil.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;

                        val l = insnNode.desc.lastIndexOf('(')
                        val desc = "${insnNode.desc.substring(0, l)}Ljava/lang/String;${insnNode.desc.substring(l)}"


                        if (
//                            asmItem.oriDesc == desc &&
                            asmItem.oriMethod == insnNode.name &&
                            insnNode.opcode == asmItem.oriAccess &&
                            (insnNode.owner == asmItem.oriClass || asmItem.oriClass == "java/lang/Object")
                        ) {
//                            val r = insnNode.desc.lastIndexOf(')')
//                            val desc = "${insnNode.desc.substring(0, r)}Ljava/lang/String;${insnNode.desc.substring(r)}"

                            logger.print(
                                "\nhook:\n" +
                                        "opcode=${insnNode.opcode},owner=${insnNode.owner},desc=${insnNode.desc},name=${klass.name}#${insnNode.name} ->\n" +
                                        "opcode=${asmItem.targetAccess},owner=${asmItem.targetClass},desc=${asmItem.targetDesc},name=${asmItem.targetMethod}\n"
                            )

                            insnNode.opcode = asmItem.targetAccess
                            insnNode.desc = asmItem.targetDesc
                            insnNode.owner = asmItem.targetClass
                            insnNode.name = asmItem.targetMethod

                            /// 将字符串压栈到第一个
                            method.instructions.insertBefore(insnNode, LdcInsnNode(asmItem.oriClass))

                            ///如果需要参数
                        }
                    }
                }
            }
        }
    }


}