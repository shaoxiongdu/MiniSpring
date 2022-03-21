/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang;

import com.lixiang.service.UserService;
import com.lixiang.spring.config.impl.ConfigurationImpl;
import com.lixiang.spring.context.XiongContext;
import com.lixiang.spring.context.impl.XiongApplicationContext;

/**
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 20:59
 */
public class Application {


    public static void main(String[] args) {

        //使用配置类的方式创建一个容器
        XiongContext xiongContext = new XiongApplicationContext(ConfigurationImpl.class);

        //获取容器中的bean
        UserService userService = (UserService) xiongContext.getBeanByName("userService");
        UserService userService1 = (UserService) xiongContext.getBeanByName("userService");

        System.out.println(userService1.equals(userService));
    }
}
