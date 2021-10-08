package com.lizhi.smartlife.plugin.base

interface ClassNameFilter {
    fun filter(className: String): Boolean
}