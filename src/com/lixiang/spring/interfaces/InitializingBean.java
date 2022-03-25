/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.interfaces;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @Date 2022/03/25
 */
public interface InitializingBean {

    /**
     * 设置属性前执行
     */
    public void afterProperties();

}
