package com.lanshifu.plugin.bean

import groovyjarjarasm.asm.Opcodes

/**
 * @author lanxiaobin
 * @date 2022-09-22
 */
class AsmClassItem(
    targetClass: String,
    methodNode: org.objectweb.asm.tree.MethodNode,
    node: org.objectweb.asm.tree.AnnotationNode
) {
    var oriClass: String = ""
    var oriMethod: String? = null
    var oriDesc: String? = null
    var oriAccess = Opcodes.INVOKESPECIAL

    var targetClass: String = ""
    var targetMethod: String = ""
    var targetDesc: String = ""
    var targetAccess = Opcodes.INVOKESPECIAL

    init {
        this.targetClass = targetClass
        this.targetMethod = methodNode.name
        this.targetDesc = methodNode.desc
        var sourceName: String = ""
        //注解是key value形式，
        for (i in 0 until node.values.size / 2) {
            val key = node.values[i * 2]
            val value = node.values[i * 2 + 1]
            if (key == "oriClass") {
                sourceName = value.toString()
                oriClass = sourceName.substring(1, sourceName.length - 1)
            } else if (key == "oriAccess") {
                oriAccess = value as Int
            } else if (key === "oriMethod") {
                oriMethod = value as String?
            } else if (key == "targetClass") {
                sourceName = value.toString()
                this.targetClass = sourceName.substring(1, sourceName.length - 1)
            }
        }

        //只要处理这里
        if (oriMethod == null) {
            oriMethod = targetMethod
        }
        oriDesc = targetDesc
    }

    override fun equals(other: Any?): Boolean {
        if (other is AsmClassItem) {
            return (other.oriAccess == oriAccess &&
                    other.oriClass == oriClass &&
                    other.oriDesc == oriDesc &&
                    other.oriMethod == oriMethod
                    )
        }

        return super.equals(other)
    }

    override fun toString(): String {
        return "AsmClassItem(oriClass=$oriClass, oriMethod=$oriMethod, oriDesc=$oriDesc, oriAccess=$oriAccess, targetClass='$targetClass', targetMethod='$targetMethod', targetDesc='$targetDesc', targetAccess=$targetAccess)"
    }


}