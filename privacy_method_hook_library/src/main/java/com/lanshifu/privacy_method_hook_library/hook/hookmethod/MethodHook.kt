package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult
import com.lanshifu.privacy_method_hook_library.log.LogUtil
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/28
 */
//object MethodHook {
//
//    @JvmStatic
//    @AsmMethodReplace(
//        oriClass = Method::class,
//        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
//    )
//    fun invoke(
//        method: Method,
//        obj: Object?,
//        vararg args: Object?,
//        callerClassName: String
//    ): Any? {
//
//        //ok
//        LogUtil.d("Method#invoke,obj=$obj")
//        if(args.size > 0){
//            args.forEach {
//                LogUtil.d("Method#invoke,args=${it}")
//            }
//        }
//
//        return method.invoke(obj, args)
//    }
//
//
//    private fun checkHook(){
//
//    }
//}