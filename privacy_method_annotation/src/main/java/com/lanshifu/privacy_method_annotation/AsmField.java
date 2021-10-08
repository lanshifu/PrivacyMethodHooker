package com.lanshifu.privacy_method_annotation;

import org.objectweb.asm.Opcodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author lanxiaobin
 * @date 2021/9/30.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface AsmField {
    Class oriClass();

    String oriMethod() default "";

    int oriAccess() default Opcodes.INVOKESTATIC;
}