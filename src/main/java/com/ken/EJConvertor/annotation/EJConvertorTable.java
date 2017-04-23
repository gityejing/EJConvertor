package com.ken.EJConvertor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * javaBean 类上的注解，对应 excel 表中一张sheet
 *
 * @author Ken
 * @since 2017/4/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EJConvertorTable {

    /**
     * 对应 sheet 的名称，默认名称为: default sheet
     *
     * @return 返回 sheet 的名称
     */
    String sheetName() default "default sheet";

    /**
     * sheet 中表头名称是否需要加粗，默认不加粗
     *
     * @return 返回一个 boolean 值，true 代表加粗，否则不加粗
     */
    boolean boldHeading() default false;
}
