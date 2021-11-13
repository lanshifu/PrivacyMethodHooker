package com.lizhi.plugin

import com.android.build.gradle.AppExtension
import com.didiglobal.booster.gradle.getAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author lanxiaobin
 * @date 2021/11/13
 */
class LizhiPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.gradle.addListener(DoKitTransformTaskExecutionListener(project))

        when {
            project.plugins.hasPlugin("com.android.application") || project.plugins.hasPlugin("com.android.dynamic-feature") -> {
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