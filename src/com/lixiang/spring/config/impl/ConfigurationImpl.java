/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.config.impl;

import com.lixiang.spring.annotation.ComponentScan;
import com.lixiang.spring.config.XiongApplicationConfig;

/**
 * 基于配置类的配置方式
 *  通过注解指定扫描的路径为 service
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 21:04
 */
@ComponentScan( componentPath = "com.lixiang.service")
public class ConfigurationImpl implements XiongApplicationConfig {
}
