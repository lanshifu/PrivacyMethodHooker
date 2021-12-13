package com.lanshifu.plugin.privacymethod

import groovyjarjarasm.asm.Opcodes

/**
 * @author lanxiaobin
 * @date 2021/9/30
 */
class AsmItem(
    targetClass: String,
    methodNode: org.objectweb.asm.tree.MethodNode,
    node: org.objectweb.asm.tree.AnnotationNode
) {
    var oriClass: String? = null
    var oriMethod: String? = null
    var oriDesc: String? = null
    var oriAccess = Opcodes.INVOKESTATIC

    var targetClass: String = ""
    var targetMethod: String = ""
    var targetDesc: String = ""
    var targetAccess = Opcodes.INVOKESTATIC

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
            }
        }
        if (oriMethod == null) {
            oriMethod = targetMethod
        }
        //静态方法，oriDesc 跟 targetDesc 一样
        if (oriAccess == Opcodes.INVOKESTATIC) {
            oriDesc = targetDesc
        } else {
            //非静态方法，约定第一个参数是实例类名，oriDesc 比 targetDesc 少一个参数，处理一下
            var param = targetDesc.split(")")[0] + ")" //(Landroid/telephony/TelephonyManager;)
            val returnValue = targetDesc.split(")")[1] //Ljava/lang/String;
            if (param.indexOf(sourceName) == 1) {
                param = "(" + param.substring(param.indexOf(sourceName) + sourceName.length)
            }
            oriDesc = param + returnValue

            //处理之后
            //targetDesc=(Landroid/telephony/TelephonyManager;)Ljava/lang/String;
            //oriDesc= Ljava/lang/String;
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is AsmItem) {
            return (other.oriAccess == oriAccess &&
                    other.oriClass == oriClass &&
                    other.oriDesc == oriDesc &&
                    other.oriMethod == oriMethod
                    )
        }

        return super.equals(other)
    }

    override fun toString(): String {
        return "AsmItem(oriClass=$oriClass, oriMethod=$oriMethod, oriDesc=$oriDesc, oriAccess=$oriAccess, targetClass='$targetClass', targetMethod='$targetMethod', targetDesc='$targetDesc', targetAccess=$targetAccess)"
    }


}