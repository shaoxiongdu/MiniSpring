/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring;

import com.lixiang.spring.enums.ScopeEnum;

/**
 * bean的定义类
 * 用来指定bean的各种属性
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/20
 */
public class BeanDefinition {

    /**
     * bean的类型
     */
    private Class clazz;

    /**
     * 单例还是多例
     */
    private ScopeEnum scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public ScopeEnum getScope() {
        return scope;
    }

    public void setScope(ScopeEnum scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "clazz=" + clazz +
                ", scope=" + scope +
                '}';
    }
}
