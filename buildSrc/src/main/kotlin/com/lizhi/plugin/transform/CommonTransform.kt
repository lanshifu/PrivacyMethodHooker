package com.lizhi.plugin.transform

import com.didiglobal.booster.transform.Transformer
import com.lizhi.plugin.classtransformer.AnnonationParserClassTransform
import com.lizhi.plugin.asmtransformer.BaseAsmTransformer
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class CommonTransform(androidProject: Project) : BaseTransform(androidProject) {

    override val transformers = listOf<Transformer>(
        BaseAsmTransformer(
            listOf(
                AnnonationParserClassTransform()
            )
        )
    )

    override fun getName(): String {
        return "CommonTransform"
    }

}
