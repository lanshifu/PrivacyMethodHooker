package com.lanshifu.plugin.transform

import com.didiglobal.booster.transform.Transformer
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import com.lanshifu.plugin.asmtransformer.BaseAsmTransformer
import com.lanshifu.plugin.classtransformer.PrivacyMethodReplaceTransform
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
@AutoService(ClassTransformer::class)
class PrivacyMethodHookTransform(androidProject: Project) : BaseTransform(androidProject) {

    override val transformers = listOf<Transformer>(
        BaseAsmTransformer(
            listOf(
                PrivacyMethodReplaceTransform()
            )
        )
    )

    override fun getName(): String {
        return "PrivacyMethodHookTransform"
    }

}
