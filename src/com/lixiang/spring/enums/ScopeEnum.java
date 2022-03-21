/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.enums;

/**
 * bean范围枚举
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/20
 */
public enum ScopeEnum {
    /**
     * 单例
     */
    单例("单例", "singleton"),
    /**
     * 多例
     */
    多例("多例", "multipleCases"),
    ;

    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String scope;

    ScopeEnum(String name, String scope) {
        this.name = name;
        this.scope = scope;
    }
}
