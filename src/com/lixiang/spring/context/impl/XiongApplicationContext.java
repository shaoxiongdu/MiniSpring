/*
 * Copyright (c) 2022.  实现一个迷你Spring 版权所有 杜少雄 github地址：https://github.com/shaoxiongdu/XiongSpring
 */

package com.lixiang.spring.context.impl;

import com.lixiang.spring.BeanDefinition;
import com.lixiang.spring.annotation.Component;
import com.lixiang.spring.annotation.ComponentScan;
import com.lixiang.spring.annotation.Scope;
import com.lixiang.spring.context.XiongContext;
import com.lixiang.spring.enums.ScopeEnum;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  实现了基础容器的增强容器
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 21:01
 */
public class XiongApplicationContext implements XiongContext {

    /**
     * 配置类
     */
    private final Class configClazz;

    /**
     * bean定义map  key bean名称  value bean定义类
     */
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 单例池
     */
    private ConcurrentHashMap<String, Object> singletonPool = new ConcurrentHashMap<>();

    /**
     * 以配置类的形式创建一个容器
     * 扫描配置类中设置的路径
     * - 如果文件是字节码文件
     * - 判断是否有component注解
     * - 如果有 创建bean定义对象 添加到bean定义map中
     *
     * @param configClazz 配置类
     */
    public XiongApplicationContext(Class configClazz) {

        System.out.println("容器构造方法执行！");

        this.configClazz = configClazz;

        //通过配置类ComponentScan注解获取需要扫描bean的路径
        if (configClazz.isAnnotationPresent(ComponentScan.class)) {

            //拿到该注解的值 即为需要扫描bean的路径
            ComponentScan componentScanAnnotation = (ComponentScan) configClazz.getAnnotation(ComponentScan.class);
            String componentPath = componentScanAnnotation.componentPath();

            //将.替换为/
            componentPath = componentPath.replace(".","/");

            //获取该路径下的Java类的字节码文件  也就是当前项目的输出路径
            //获取当前类的类加载器
            ClassLoader classLoader = XiongApplicationContext.class.getClassLoader();
            //通过类加载器 传入相对路径获取对应的绝对路径
            URL resource = classLoader.getResource(componentPath);
            //bean对应的文件夹
            File file = new File(resource.getFile());
            //通过打印 我们得到了bean字节码的绝对路径 /Users/dushaoxiong/workspace/XiongSpring/out/production/XiongSpring/com/lixiang/service
            System.out.println("需要扫描的路径： " + file.getAbsolutePath());
            if(file.isDirectory()){
                //拿到所有子文件
                File[] files = file.listFiles();
                for (File f : files) {

                    //绝对路径
                    String absolutePath = f.getAbsolutePath();
                    // /Users/dushaoxiong/workspace/XiongSpring/out/production/XiongSpring/com/lixiang/service/UserService.class
                    System.out.println("扫描到到文件的绝对路径：" + absolutePath);
                    //如果是字节码文件
                    if(absolutePath.endsWith(".class")){

                        //将绝对路径转化为相对包路径 截取 包扫描路径 到 .class
                        String relativelyClassPath = absolutePath.substring(absolutePath.indexOf(componentPath), absolutePath.indexOf(".class"));
                        //将 `/` 转化为 `.`
                        relativelyClassPath = relativelyClassPath.replace("/",".");

                        Class<?> clazz = null;
                        try {
                            //通过上边的类加载器 用字节码的绝对路径加载字节码到内存中
                            clazz = classLoader.loadClass(relativelyClassPath);

                            //判断当前类是否有component注解
                            if (clazz.isAnnotationPresent(Component.class)) {
                                System.out.println("类：" + clazz + " 有Component注解 需要我们加入容器中！");

                                //获取bean名称
                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                String beanName = componentAnnotation.beanName();
                                //生成bean定义对象
                                BeanDefinition beanDefinition = new BeanDefinition();
                                //设置类型
                                beanDefinition.setClazz(clazz);

                                //单例还是多例
                                //如果有scope注解
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    //获取注解对象
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    //设置为注解中标注的值
                                    beanDefinition.setScope(scopeAnnotation.scope());
                                } else {
                                    //如果没有指定 则默认为单例
                                    beanDefinition.setScope(ScopeEnum.单例);
                                }

                                //添加到bean定义map中
                                beanDefinitionMap.put(beanName, beanDefinition);

                            } else {
                                System.out.println(clazz.getName() + "没有component注解，不需要创建！");
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            System.out.println("bean定义map:" + beanDefinitionMap);
        }

        //创建bean定义map中的所有单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            //如果是单例
            if (beanDefinition.getScope().equals(ScopeEnum.单例)) {
                //创建bean
                Object bean = createBean(beanName, beanDefinition);
                //添加到单例池
                singletonPool.put(beanName, bean);
                System.out.println("容器启动后，单例bean" + beanName + ":创建成功，并放入单例池中！");
            }
        }
    }

    /**
     * 通过名称获取bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    @Override
    public Object getBeanByName(String beanName) {

        //通过bean名称获取对应的bean定义
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        //如果没有bean 抛出异常
        if (Objects.isNull(beanDefinition)) {
            throw new NullPointerException("容器中没有名称为" + beanName + "的bean!");
        }

        //判断单例还是多例
        ScopeEnum scope = beanDefinition.getScope();
        if (scope.equals(ScopeEnum.单例)) {
            //如果是单例 从单例池中获取
            Object bean = singletonPool.get(beanName);
            //如果单例池不存在
            if (Objects.isNull(bean)) {
                //创建
                bean = createBean(beanName, beanDefinition);
                //放入单例池
                singletonPool.put(beanName, bean);
            }
            //返回
            return bean;

        } else if (scope.equals(ScopeEnum.多例)) {
            //如果是多例 创建一个bean
            Object bean = createBean(beanName, beanDefinition);
            System.out.println("获取多例bean:" + beanName + "，创建成功！");
            //返回
            return bean;
        }
        return null;
    }


    /**
     * 利用反射创建一个bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义对象
     * @return 创建好的bean
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();

        //获取clazz对应的无残构造
        Constructor constructor = null;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            System.out.println(beanName + "没有无参构造方法！创建失败");
        }

        //通过构造方法创建bean对象
        Object bean = null;
        try {
            bean = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回
        return bean;
    }

    /**
     * 通过类型获取bean
     *
     * @param clazz bean类型
     * @return bean
     */
    @Override
    public Object getBeanByClass(Class clazz) {
        return null;
    }
}