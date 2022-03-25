/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.service;

import com.lixiang.spring.annotation.Component;
import com.lixiang.spring.interfaces.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/25
 */
@Component
public class XiongBeanPostProcessorImpl implements BeanPostProcessor {


    /**
     * 每个bean初始化之前执行
     *
     * @param beanName bean名称
     * @param bean     bean对象
     */
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        System.out.println("\t容器中的bean[" + beanName + "] 初始化之前  XiongBeanPostProcessBeforeInitialization 执行！");
        return bean;
    }

    /**
     * 每个bean初始化之后
     *
     * @param beanName bean名称
     * @param bean     bean对象
     */
    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        System.out.println("\t容器中的bean[" + beanName + "] 初始化之后 XiongBeanPostProcessAfterInitialization 执行！");

        //如果当前bean是userService 则创建一个代理对象并返回
        if("userService".equals(beanName)){
            Object proxyInstance = Proxy.newProxyInstance(XiongBeanPostProcessorImpl.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("\t切面逻辑...");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }

}
