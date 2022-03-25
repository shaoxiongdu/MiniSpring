/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.interfaces;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/25
 */
public interface BeanPostProcessor {

    /**
     * 初始化之前执行
     * @param beanName bean名称
     * @param bean bean对象
     */
    public Object postProcessBeforeInitialization(String beanName,Object bean);

    /**
     * 初始化之后
     * @param beanName bean名称
     * @param bean bean对象
     */
    public Object postProcessAfterInitialization(String beanName,Object bean);

}
