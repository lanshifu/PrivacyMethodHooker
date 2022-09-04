package com.lanshifu.asm_annotation

import org.objectweb.asm.Opcodes

/**
 * @author lanxiaobin
 * @date 2021/10/10
 *
 * Opcodes 转换，业务可以不依赖 'org.ow2.asm:asm:7.1'
 */
object AsmMethodOpcodes {

    const val INVOKESTATIC = Opcodes.INVOKESTATIC
    const val INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL
    const val INVOKESPECIAL = Opcodes.INVOKESPECIAL
    const val INVOKEDYNAMIC = Opcodes.INVOKEDYNAMIC
    const val INVOKEINTERFACE = Opcodes.INVOKEINTERFACE
}