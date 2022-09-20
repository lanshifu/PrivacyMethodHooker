package com.lanshifu.plugin.transform

import com.didiglobal.booster.transform.Transformer
import com.lanshifu.plugin.classtransformer.AnnotationParserClassTransform
import com.lanshifu.plugin.asmtransformer.BaseAsmTransformer
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class CommonTransform(androidProject: Project) : BaseTransform(androidProject) {

    override val transformers = listOf<Transformer>(
        BaseAsmTransformer(
            listOf(
                AnnotationParserClassTransform()
            )
        )
    )

    override fun getName(): String {
        return "CommonTransform"
    }

}
