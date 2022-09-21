package com.lanshifu.plugin.classtransformer

import com.didiglobal.booster.kotlinx.file
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.lanshifu.plugin.bean.AsmClassItem
import com.lanshifu.plugin.bean.AsmMethodItem
import org.objectweb.asm.tree.ClassNode
import java.io.PrintWriter

/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
class AnnotationParserTransformer : AbsClassTransformer() {

    private lateinit var logger: PrintWriter

    companion object{
        const val AsmFieldDesc = "Lcom/lanshifu/asm_annotation/AsmMethodReplace;"
        const val AsmClassReplaceDesc = "Lcom/lanshifu/asm_annotation/AsmClassReplace;"
        var asmMethodReplaceConfigs = mutableListOf<AsmMethodItem>()
        var asmClassReplaceConfigs = mutableListOf<AsmClassItem>()
        var asmConfigsMap = HashMap<String,String>()
    }

    override fun onPreTransform(context: TransformContext) {
        super.onPreTransform(context)
        this.logger = context.reportsDir.file("AnnotationParserClassTransform").file(context.name).file("report.txt").touch().printWriter()
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

        klass.methods.forEach { method->
            method.invisibleAnnotations?.forEach { node ->
                if (node.desc == AsmFieldDesc) {
                    val asmItem = AsmMethodItem(klass.name, method, node)
                    if (!asmMethodReplaceConfigs.contains(asmItem)) {
                        logger.print("\nadd AsmMethodItem:$asmItem")
                        asmMethodReplaceConfigs.add(asmItem)
                        asmConfigsMap[klass.name] = klass.name
                    }
                } else if(node.desc == AsmClassReplaceDesc){
                    //todo
                    val asmItem = AsmClassItem(klass.name, method, node)
                    if (!asmClassReplaceConfigs.contains(asmItem)) {
                        logger.print("\nadd AsmClassItem:$asmItem")
                        asmClassReplaceConfigs.add(asmItem)
                        asmConfigsMap[klass.name] = klass.name
                    }
                }
            }
        }
    }
}