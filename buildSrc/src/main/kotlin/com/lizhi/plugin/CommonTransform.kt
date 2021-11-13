package com.lizhi.plugin

import com.didiglobal.booster.transform.Transformer
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import com.lizhi.plugin.privacy_method.AnnonationParserTransform
import com.lizhi.plugin.privacy_method.DoKitAsmTransformer
import com.lizhi.plugin.privacy_method.PrivacyMethodReplaceTransform
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class CommonTransform(androidProject: Project) : DoKitBaseTransform(androidProject) {

    override val transformers = listOf<Transformer>(DoKitAsmTransformer(
            listOf(
                AnnonationParserTransform()
            )
        )
    )

    override fun getName(): String {
        return "CommonTransform"
    }

}
