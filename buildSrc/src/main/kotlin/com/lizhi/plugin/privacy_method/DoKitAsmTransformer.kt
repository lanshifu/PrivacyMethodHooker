package com.lizhi.plugin.privacy_method

import com.didiglobal.booster.transform.asm.ClassTransformer
import com.lizhi.plugin.BaseDoKitAsmTransformer

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class DoKitAsmTransformer(transformers: Iterable<ClassTransformer>) :
    BaseDoKitAsmTransformer(transformers)
