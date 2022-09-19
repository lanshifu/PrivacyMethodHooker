package com.lanshifu.asm_annotation;

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
public @interface AsmMethodReplace {
    Class<?> oriClass();

    String oriMethod() default "";

    int oriAccess() default AsmMethodOpcodes.INVOKESTATIC;

    boolean needClassNameParam() default false;
}