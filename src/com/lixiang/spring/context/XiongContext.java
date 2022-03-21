/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.context;

/**
 * 最基础的容器
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 21:16
 */
public interface XiongContext {

    /**
     * 通过名称获取bean
     * @param beanName
     * @return
     */
    Object getBeanByName(String beanName);

    /**
     * 通过类型获取bean
     * @param clazz
     * @return
     */
    Object getBeanByClass(Class clazz);
}
