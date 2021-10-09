package com.lizhi.smartlife.plugin.privacy_method

import com.lizhi.smartlife.plugin.base.Log.info
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
        if (oriAccess == Opcodes.INVOKESTATIC) { //静态方法，参数和返回值一致
            oriDesc = targetDesc
        } else {
            //targetDesc=(Landroid/telephony/TelephonyManager;)Ljava/lang/String;
            var param = targetDesc.split(")")[0] + ")" //(Landroid/telephony/TelephonyManager;)
            val returnValue = targetDesc.split(")")[1] //Ljava/lang/String;
            if (param.indexOf(sourceName) == 1) {
                param = "(" + param.substring(param.indexOf(sourceName) + sourceName.length)
            }
            oriDesc = param + returnValue
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