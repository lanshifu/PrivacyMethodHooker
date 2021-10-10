package com.lizhi.smartlife.plugin.privacy_method

import com.lizhi.smartlife.plugin.base.AsmHelper
import com.lizhi.smartlife.plugin.base.Log.info
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

class AnnotationParserAsmHelper : AsmHelper {

    companion object {

        private const val AsmFieldDesc = "Lcom/lanshifu/privacy_method_annotation/AsmField;"

        var asmConfigs = mutableListOf<AsmItem>()
        var ignoreClassList = mutableListOf<String>()
    }

    override fun modifyClass(srcClass: ByteArray?): ByteArray {
        val classNode = ClassNode(Opcodes.ASM5)
        val classReader = ClassReader(srcClass)
        //1 将读入的字节转为classNode
        classReader.accept(classNode, 0)
        //2 对classNode的处理逻辑

        //todo AsmClass注解处理
//        classNode.invisibleTypeAnnotations?.forEach { node ->
////            if (node.desc == )
//        }

        val methods = classNode.methods
        methods.forEach { method ->
            method.invisibleAnnotations?.forEach { node ->
                if (node.desc == AsmFieldDesc) {
                    val asmItem = AsmItem(classNode.name, method, node)
                    if (!asmConfigs.contains(asmItem)) {
                        info("add AsmItem:$asmItem")
                        asmConfigs.add(asmItem)
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
