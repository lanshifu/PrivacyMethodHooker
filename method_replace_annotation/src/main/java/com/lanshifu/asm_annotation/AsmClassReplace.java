package com.lanshifu.asm_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author lanxiaobin
 * @date 2021/9/30.
 *
 * INVOKESPECIAL java/io/File.<init> (Ljava/lang/String;)V
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface AsmClassReplace {
    Class<?> oriClass();

    int oriAccess() default AsmMethodOpcodes.INVOKESPECIAL;

    boolean needClassNameParam() default false;
}