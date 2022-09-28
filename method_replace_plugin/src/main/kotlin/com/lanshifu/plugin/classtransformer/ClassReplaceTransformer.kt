package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.find
import com.didiglobal.booster.transform.asm.isInstanceOf
import com.lanshifu.plugin.bean.AsmClassItem
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.TypeInsnNode
import java.io.PrintWriter


/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class ClassReplaceTransformer : AbsClassTransformer() {
    private lateinit var logger: PrintWriter
    private var asmItemsClassMap: HashMap<String, AsmClassItem> = HashMap()

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("ClassReplaceTransformer").file(context.name)
            .file("report.txt").touch().printWriter()
        logger.println("--start-- ${System.currentTimeMillis()}")

        AnnotationParserTransformer.asmClassReplaceConfigs.forEach {
            asmItemsClassMap[it.oriClass] = it
        }
        logger.print("\nasmItemsMap size=${asmItemsClassMap.size}\n\n")
        logger.print("asmItemsClassMap=${asmItemsClassMap} \n\n")
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
                        if (insnNode is TypeInsnNode) {
                            insnNode.find { findInsnNode ->
                                (findInsnNode.opcode == Opcodes.INVOKESPECIAL) &&
                                        (findInsnNode is MethodInsnNode) &&
                                        (insnNode.desc == findInsnNode.owner && "<init>" == findInsnNode.name)
                            }?.isInstanceOf { init: MethodInsnNode ->

                                asmItemsClassMap[init.owner]?.let { asmItem ->
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
                                        method.instructions.insertBefore(
                                            init,
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
    }


}