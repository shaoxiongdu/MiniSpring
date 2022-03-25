# 实现一个简单的迷你Spring

# 大致思路


### 1. 项目结构

```tex
├── Application.java											//程序主入口
├── service																//业务
│   ├── UserController.java
│   ├── UserDao.java
│   └── UserService.java
└── spring
    ├── BeanDefinition.java								//类 bean的定义类 说明这里bean的各种属性。
    ├── annotation												// 注解相关包
    │   ├── Autowired.java 								//自动注入注解
    │   ├── Component.java								//表示这是一个bean的注解
    │   ├── ComponentScan.java  					//配置类中表示需要扫描的路径注解
    │   └── Scope.java										//直接表示bean是 单例还是多例
    ├── config														//配置相关包
    │   ├── XiongApplicationConfig.java		//容器的配置 接口
    │   └── impl													//实现
    │       ├── ConfigurationImpl.java    //配置类形式的实现
    │       └── XmlImpl.java							//读取外部xml文件的实现
    ├── context														//容器相关包
    │   ├── XiongContext.java							//容器顶级接口
    │   └── impl													//容器实现
    │       └── XiongApplicationContext.java //实现了容器接口的增强容器
    └── enums															//枚举包
        └── ScopeEnum.java								//单例还是多例枚举类

```

### 2. 实现增强容器的构造方法

1. 从配置类中读取ComponentScan的值 扫描该路径
2. 通过反射循环创建该路径下的所有Java类

   1. 判断是否有Component注解。
      - 如果有
        1. 则创建对应的bean定义对象
        2. 从注解中获取bean名称,bean类型及是否是单例，bean类型等。
           1. 添加到bean定义map中
        3. 判断该bean是否实现了BeanPostProcessor接口
           1. 如果实现了，添加到对应的list中。

3. 创建bean定义map中的所有单例bean
   1. 循环map
      - 如果当前是单例bean

          1. 调用创建bean的方法

          2. 放入单例池 `singletonPool`中

### 3. 实现增强容器创建bean方法

1. 通过参数中的bean定义对象从bean定义map中获取对应的bean类
2. 利用反射通过bean类获取无参构造方法
    - 如果获取失败，抛出异常
3. 通过无参构造创建实例
4. 解决依赖注入

    1. 利用反射循环该实例的所有属性

        - 如果属性上有AutoWirted注解 

          1. 通过属性名称去容器中获取对应的bean
          2. 设置属性的访问权限为可强制访问
          3. 将容器中获取的bean设置到该属性中。
5. 判断改实例是否实现了BeanNameAware接口
    1. 如果实现了，则调用对应的setBeanName方法。
6. 执行所有的前置处理器  传入beanName 和 bean，返回值赋值给该实例对象
7. 如果该bean实现了initializingBean接口
    1. 调用对应的方法

8. 执行所有的后置处理器，返回值赋值给该实例对象
9. 返回该实例

### 4. 实现增强容器getBean(String beanName)方法

- 判断bean定义map中是否有该beanName的key
    - 如果有
        - 如果是单例
            - 从容器中根据key(beanName)获取
                - 如果返回值不为null 返回bean
                - 如果为空，调用createBean方法 返回
        - 如果是多例
            - 创建bean并返回
    - 如果没有 抛出异常 `容器中没有该bean`

### 5. 实现增强容器getBean(Class clazz)方法

- 循环bean定义map
  - 如果value(beanDefiniation)中的clazz和参数相同
    - 获取当前元素的key 即为beanName
      - 通过getBean(String beanName)获取bean并返回
  - 如果不同 结束此次循环
- 循环结束，抛出 容器中没有该类型的bean异常

### 6. 实现增强容器getAllBeanName()方法

- 将bean定义map的key转为list返回即可
