/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.annotation;

import com.lixiang.spring.enums.ScopeEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/20
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    ScopeEnum scope() default ScopeEnum.单例;

}
