package com.lizhi.smartlife.plugin.base

import org.gradle.api.Plugin
import org.gradle.api.Project

interface PluginProvider {

    fun getPlugin(): Class<out Plugin<Project>>


    fun dependOn(): List<String>
}