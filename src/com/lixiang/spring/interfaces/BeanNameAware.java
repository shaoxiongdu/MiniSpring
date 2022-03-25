/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.interfaces;

/**
 * 在Spring给bean设置名称之后 会回调
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/23
 */
public interface BeanNameAware {

    /**
     * 设置bean名称
     * @param beanName
     */
    public void setBeanName(String beanName);

}
