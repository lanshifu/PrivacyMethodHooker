package com.lanshifu.privacymethodhooker.class_hook_test

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/30
 */
open class Dog(var param1:String) {

    init {
        Log.i("Dog","init")
    }

    open fun eat(){
        Log.i("Dog","eat")
    }
}