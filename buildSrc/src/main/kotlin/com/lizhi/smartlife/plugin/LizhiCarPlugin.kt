package com.lizhi.smartlife.plugin

import com.android.build.gradle.AppExtension
import com.lizhi.smartlife.plugin.privacy_method.AnnonationParserTransform
import com.lizhi.smartlife.plugin.privacy_method.PrivacyMethodReplaceTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/1/23
 */
class LizhiCarPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        //读取配置

        println("LizhiCarPlugin->apply")

        val appExtension = project.extensions.getByType(AppExtension::class.java)

        // todo 只在release版本生效

        appExtension.registerTransform(
            AnnonationParserTransform(project)
        )

        appExtension.registerTransform(
            PrivacyMethodReplaceTransform(project)
        )
    }
}