/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.service;

import com.lixiang.spring.annotation.Autowired;
import com.lixiang.spring.annotation.Component;
import com.lixiang.spring.annotation.Scope;
import com.lixiang.spring.enums.ScopeEnum;
import com.lixiang.spring.interfaces.BeanNameAware;
import com.lixiang.spring.interfaces.InitializingBean;

/**
 * 利用注解指定bean名称
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 21:15
 */
@Component
@Scope(scope = ScopeEnum.单例)
public class UserService implements BeanNameAware , InitializingBean ,UserServiceInterface{

    @Autowired
    private UserDao userDao;

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                '}';
    }

    /**
     * 设置bean名称
     *
     * @param beanName
     */
    @Override
    public void setBeanName(String beanName) {
        System.out.println("userService的BeanNameAware执行：" + beanName);
    }

    /**
     * 设置属性前执行
     */
    @Override
    public void afterProperties() {
        System.out.println("userService bean初始化方法被执行！");
    }

    @Override
    public void test() {

    }
}
