package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.PrintWriter


/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class ClassReplaceTransformer : AbsClassTransformer() {
    private lateinit var logger: PrintWriter
    private val asmItems = AnnotationParserTransformer.asmClassReplaceConfigs
    private var asmItemsClassMap: HashMap<String, String> = HashMap()

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("ClassReplaceTransformer").file(context.name)
            .file("report.txt").touch().printWriter()
        logger.println("--start-- ${System.currentTimeMillis()}")

        AnnotationParserTransformer.asmClassReplaceConfigs.forEach {
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

        if (AnnotationParserTransformer.asmConfigsMap.contains(klass.name)) {
            logger.print("\nPrivacyMethodReplaceAsmHelper modifyClass ignore,classNode.name=${klass.name}\n")
            return@also
        }

        klass.methods.forEach { method ->
            method.instructions?.iterator()?.forEach { insnNode ->

                if (insnNode is MethodInsnNode && insnNode.name == "<init>") {

                    asmItems.forEach { asmItem ->
                        //INVOKEVIRTUAL android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List; ->
                        //INVOKESTATIC  com/lanshifu/asm_plugin_library/privacy/PrivacyUtil.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;
//AsmMethodItem(
// oriClass=java/io/File,
// oriMethod=hookFile,
// oriDesc=(Ljava/lang/String;)Ljava/io/File;,
// oriAccess=183,
// targetClass='com/lanshifu/privacy_method_hook_library/hook/DeviceIdHook',
// targetMethod='hookFile',
// targetDesc='(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;',
// targetAccess=183)]

//if:
//opcode=183,
// owner=java/io/File,
// desc=(Ljava/lang/String;)V,
// klass.name=androidx/core/net/UriKt#insnNode.name=<init> ->
                        if (
                            asmItem.oriDesc == insnNode.desc &&
                            insnNode.opcode == asmItem.oriAccess &&
                            insnNode.owner == asmItem.oriClass
                        ) {

                            logger.print(
                                "\nhook:\n" +
                                        "owner=${insnNode.owner},desc=${insnNode.desc} klass.name=${klass.name}->\n" +
                                        "owner=${asmItem.targetClass},desc=${asmItem.targetDesc}\n"
                            )

                            insnNode.desc = asmItem.targetDesc
                            insnNode.owner = asmItem.targetClass

                            method.instructions.insertBefore(insnNode, LdcInsnNode(klass.name))
                        }
                    }
                }
            }
        }
    }


}