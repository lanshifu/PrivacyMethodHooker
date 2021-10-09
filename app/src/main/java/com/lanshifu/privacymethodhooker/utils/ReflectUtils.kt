package com.lanshifu.privacymethodhooker.utils

import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2021/10/5
 */
object ReflectUtils {

//    fun <T> invokeSuperMethod(
//        obj: Any,
//        name: String,
//        types: Array<Class<*>>,
//        args: Array<Any?>?
//    ): Any? {
//        try {
//            val method: Method? = getMethod(obj.javaClass.superclass, name, types)
//            if (null != method) {
//                method.isAccessible = true
//                return method.invoke(obj, args)
//            }
//        } catch (t: Throwable) {
//            t.printStackTrace()
//        }
//        return null
//    }

    private fun getMethod(klass: Class<*>, name: String, types: Array<Class<*>>): Method? {
        return try {
            klass.getDeclaredMethod(name, *types)
        } catch (e: NoSuchMethodException) {
            val parent = klass.superclass ?: return null
            getMethod(parent, name, types)
        }
    }

    @JvmStatic
    fun <T> invokeMethod(obj: Any, name: String, types: Array<Class<*>>, args: Array<Any?>): Any? {
        try {
            val method = getMethod(obj.javaClass, name, types)
            if (null != method) {
                method.isAccessible = true
                return method.invoke(obj, *args)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return null
    }
}