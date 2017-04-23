package com.ken.EJConvertor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注 javaBean 中需要转换到 excel 中列的 field
 *
 * @author Ken
 * @since 2017/4/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EJConvertorColumn {

    /**
     * javaBean 标注的 field 对应的列的标题
     *
     * @return 返回标注的 field 对应的列的标题
     */
    String columnTitle();

}
