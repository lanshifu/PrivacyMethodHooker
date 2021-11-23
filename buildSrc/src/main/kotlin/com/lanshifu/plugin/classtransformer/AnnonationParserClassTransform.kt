package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.lanshifu.plugin.privacymethod.AsmItem
import org.objectweb.asm.tree.ClassNode
import java.io.PrintWriter

/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class AnnonationParserClassTransform : AbsClassTransformer() {

    private lateinit var logger: PrintWriter

    companion object{
        const val AsmReplaceClass= "Lcom/lanshifu/privacy_method_annotation/AsmClass;"
        const val AsmFieldDesc = "Lcom/lanshifu/privacy_method_annotation/AsmField;"
        var asmConfigs = mutableListOf<AsmItem>()
        var asmConfigsMap = HashMap<String,String>()
    }

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("AnnonationParserTransform").file(context.name).file("report.txt").touch().printWriter()
        logger.println("--start-- ${System.currentTimeMillis()}")
    }

    override fun onPostTransform(context: TransformContext) {
        logger.println("\n--end-- ${System.currentTimeMillis()}")
        this.logger.close()
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {
        if (onCommInterceptor(context, klass)) {
            return klass
        }

        klass.invisibleAnnotations?.forEach {
            if (it.desc == AsmReplaceClass){
                asmConfigsMap.put(klass.name,klass.name)
                logger.print("klass.invisibleAnnotations,klass.name=${klass.name}")
            }
        }

        klass.methods.forEach { method->
            method.invisibleAnnotations?.forEach { node ->
                if (node.desc == AsmFieldDesc) {
                    val asmItem = AsmItem(klass.name, method, node)
                    if (!asmConfigs.contains(asmItem)) {
                        logger.print("\nadd AsmItem:$asmItem")
                        asmConfigs.add(asmItem)
                        asmConfigsMap.put(klass.name,klass.name)
                    }
                }
            }
        }
    }
}