/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dushaoxiong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * bean名称
     *
     * @return
     */
    String beanName() default "";

    /**
     * bean类型
     * @return
     */
    Class beanClazz() default Object.class;
}
