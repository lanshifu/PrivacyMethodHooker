package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.find
import com.didiglobal.booster.transform.asm.isInstanceOf
import com.lanshifu.plugin.bean.AsmClassItem
import com.lanshifu.plugin.bean.AsmMethodItem
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.io.PrintWriter


/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class MethodReplaceTransformer : AbsClassTransformer() {
    private lateinit var logger: PrintWriter
    private val asmItems = AnnotationParserTransformer.asmMethodReplaceConfigs
    private var asmMethodItemsMap: HashMap<String, AsmMethodItem> = HashMap()
    private var asmClassItemsMap: HashMap<String, AsmClassItem> = HashMap()

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("PrivacyMethodReplaceTransform").file(context.name)
            .file("report.txt").touch().printWriter()
        logger.println("--start-- ${System.currentTimeMillis()}")

        AnnotationParserTransformer.asmMethodReplaceConfigs.forEach {
            asmMethodItemsMap["${it.oriClass}${it.oriMethod}${it.oriDesc}"] = it
        }

        AnnotationParserTransformer.asmClassReplaceConfigs.forEach {
            asmClassItemsMap[it.oriClass] = it
        }

        logger.print("\nasmItemsMap size=${asmMethodItemsMap.size}，asmItems.size=${asmItems.size}\n\n")
        logger.print("asmItemsClassMap=${asmMethodItemsMap} \n\n")
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

                when (insnNode.opcode) {
                    Opcodes.NEW -> {
                        transformNew(klass, insnNode)
                    }
                    else -> {
                        if (insnNode is MethodInsnNode) {

                            asmMethodItemsMap["${insnNode.owner}${insnNode.name}${insnNode.desc}"]?.let { asmItem ->
                                if (
                                    asmItem.oriDesc == insnNode.desc &&
                                    asmItem.oriMethod == insnNode.name &&
                                    insnNode.opcode == asmItem.oriAccess &&
                                    (insnNode.owner == asmItem.oriClass || asmItem.oriClass == "java/lang/Object")
                                ) {

                                    logger.print(
                                        "\nhook:\n" +
                                                "opcode=${insnNode.opcode},owner=${insnNode.owner},desc=${insnNode.desc},name=${klass.name}#${insnNode.name} ->\n" +
                                                "opcode=${asmItem.targetAccess},owner=${asmItem.targetClass},desc=${asmItem.targetDesc},name=${asmItem.targetMethod}\n"
                                    )

                                    insnNode.opcode = asmItem.targetAccess
                                    insnNode.desc = asmItem.targetDesc
                                    insnNode.owner = asmItem.targetClass
                                    insnNode.name = asmItem.targetMethod

                                    method.instructions.insertBefore(
                                        insnNode,
                                        LdcInsnNode(klass.name)
                                    )
                                }
                            }

                        }
                    }
                }


            }
        }
    }

    private fun transformNew(klass: ClassNode, insnNode: AbstractInsnNode) {
        if (insnNode is TypeInsnNode) {
            insnNode.find { findInsnNode ->
                (findInsnNode.opcode == Opcodes.INVOKESPECIAL) &&
                        (findInsnNode is MethodInsnNode) &&
                        (insnNode.desc == findInsnNode.owner && "<init>" == findInsnNode.name)
            }?.isInstanceOf { init: MethodInsnNode ->

                asmClassItemsMap[init.owner]?.let { asmItem ->


                    /// todo 有继承的情况？改superName
                    //        if (!name.equals(MONITOR_SUPER_CLASS_NAME) && superName.equals(IMAGE_VIEW_CLASS_NAME)) {
                    //            superName = MONITOR_SUPER_CLASS_NAME;
                    //        }

                    if (
                        init.desc == asmItem.oriDesc &&
                        init.owner == asmItem.oriClass
                    ) {
                        logger.print(
                            "\nhook:\n" +
                                    "owner=${init.owner},desc=${init.desc} klass.name=${klass.name}->\n" +
                                    "owner=${asmItem.targetClass},desc=${asmItem.targetDesc}\n"
                        )
                        /// 换一个类名
                        insnNode.desc = asmItem.targetClass
                        init.desc = asmItem.targetDesc
                        init.owner = asmItem.targetClass
                    }
                }

            }
        }
    }


}