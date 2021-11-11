package com.lizhi.smartlife.plugin.privacy_method

import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import org.objectweb.asm.tree.ClassNode

/**
 * @author lanxiaobin
 * @date 2021/11/11
 */
@Priority(0)
@AutoService(ClassTransformer::class)
class AnnonationParserTransform : ClassTransformer {

    companion object{
        private const val AsmFieldDesc = "Lcom/lanshifu/privacy_method_annotation/AsmField;"
        var asmConfigs = mutableListOf<AsmItem>()
        var asmConfigsMap = HashMap<String,String>()
    }

    override fun transform(context: TransformContext, klass: ClassNode) = klass.also {
        klass.methods.forEach { method->
            method.invisibleAnnotations?.forEach { node ->
                print("\r\nAnnonationParserTransform invisibleAnnotations,desc=:${node.desc}")
                if (node.desc == AsmFieldDesc) {
                    val asmItem = AsmItem(klass.name, method, node)
                    if (!asmConfigs.contains(asmItem)) {
                        print("AnnonationParserTransform add AsmItem:$asmItem")
                        asmConfigs.add(asmItem)
                        asmConfigsMap.put(klass.name,klass.name)
                    }
                }
            }
        }
    }
}