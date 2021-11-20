package com.lanshifu.plugin

import com.android.build.gradle.AppExtension
import com.didiglobal.booster.gradle.getAndroid
import com.lanshifu.plugin.transform.CommonTransform
import com.lanshifu.plugin.transform.PrivacyMethodHookTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class Plugin : Plugin<Project> {
    override fun apply(project: Project) {

        when {
            project.plugins.hasPlugin("com.android.application") ||
                    project.plugins.hasPlugin("com.android.dynamic-feature") -> {

                project.getAndroid<AppExtension>().let { androidExt ->

                    androidExt.registerTransform(
                        CommonTransform(project)
                    )
                    androidExt.registerTransform(
                        PrivacyMethodHookTransform(project)
                    )


                }

            }
        }


    }
}