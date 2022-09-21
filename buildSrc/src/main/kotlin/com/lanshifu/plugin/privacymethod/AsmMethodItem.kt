package com.lanshifu.plugin.privacymethod

import groovyjarjarasm.asm.Opcodes

/**
 * @author lanxiaobin
 * @date 2021/9/30
 */
class AsmMethodItem(
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

        //只要处理这里
        if (oriMethod == null) {
            oriMethod = targetMethod
        }

        /**由targetDesc 计算出oriDesc,，区分静态方法和非静态方法*/
        //静态方法，oriDesc = targetDesc去掉最后一个String参数
        //targetDesc=(Landroid/content/ContentResolver;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //oriDesc=(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;
        if (oriAccess == Opcodes.INVOKESTATIC) {
            val rp = targetDesc.lastIndexOf(')')
            var left = targetDesc.substring(0, rp)
            left = left.substring(0, left.lastIndexOf("Ljava/lang/String;"))
            val right = targetDesc.substring(rp)
            oriDesc = left + right
        } else {
            //非静态方法，oriDesc = targetDesc去掉前后两个参数
            //targetDesc=(Landroid/telephony/TelephonyManager;Ljava/lang/String;)Ljava/lang/String;
            //oriDesc= Ljava/lang/String;
            val returnValue = targetDesc.split(")")[1] //返回的不用改Ljava/util/List;
            var param = targetDesc.split(")")[0] + ")"
            param = "(" + param.substring(param.indexOf(sourceName) + sourceName.length)
            param = param.substring(0, param.lastIndexOf("Ljava/lang/String;")) + ')'
            oriDesc = param + returnValue

        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is AsmMethodItem) {
            return (other.oriAccess == oriAccess &&
                    other.oriClass == oriClass &&
                    other.oriDesc == oriDesc &&
                    other.oriMethod == oriMethod
                    )
        }

        return super.equals(other)
    }

    override fun toString(): String {
        return "AsmMethodItem(oriClass=$oriClass, oriMethod=$oriMethod, oriDesc=$oriDesc, oriAccess=$oriAccess, targetClass='$targetClass', targetMethod='$targetMethod', targetDesc='$targetDesc', targetAccess=$targetAccess)"
    }


}