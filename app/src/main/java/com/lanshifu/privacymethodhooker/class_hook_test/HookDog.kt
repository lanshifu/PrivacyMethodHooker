package com.lanshifu.privacymethodhooker.class_hook_test

import android.util.Log
import com.lanshifu.asm_annotation.AsmClassReplace
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/30
 */
object DogHook {

    @AsmClassReplace(oriClass = Dog::class, targetClass = HookDog::class)
    fun hookDog(params: String) {
    }

}

class HookDog(params: String) : Dog(params) {

    init {
        Log.i("Dog", "HookDog init")
    }

    override fun eat() {
//        super.eat()
        Log.i("Dog", "HookDog eat")
    }
}