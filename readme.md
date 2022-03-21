# 实现一个简单的迷你Spring

# 大致思路


### 1. 定义主要的结构及注解

- `XiongContext` `接口`：所有容器的公共接口

- `XiongApplicationContext ` `实现`：实现了容器接口的普通容器
- `XiongApplicationConfig` `接口` 容器的配置 接口
- `ConfigurationImpl` `实现` 配置类形式的实现
    - `ComponentScan` `注解` 配置类中表示需要扫描的路径注解

- `XmlImpl``实现` 读取外部xml文件的实现
- `BeanDeffinition` `bean的定义类` 说明这里bean的各种属性。包括bean类型，单例还是多例等。

- `Component` `注解` 表示这是一个bean的注解

- `Scope` `注解` 直接表示bean是 单例还是多例

- `ScopeEnum` 单例还是多例枚举类

### 2. 实现容器的构造方法

1. 从配置类中读取ComponentScan的值 扫描该路径

2. 通过反射循环创建该路径下的所有Java类

   1. 判断是否有Component注解。

- 如果有

  1. 则创建对应的bean定义对象
    1. 从注解中获取bean名称,bean类型及是否是单例，bean类型等。

        2. 添加到bean定义map中

   1. 创建bean定义map中的所有单例bean

       - 循环map

           - 如果当前是单例bean

               1. 调用创建bean的方法

               2. 放入单例池 `singletonPool`中

### 3. 实现创建bean方法

1. 通过参数中的bean定义对象获取对应的bean类
2. 利用反射通过bean类获取无参构造方法
    - 如果获取失败，抛出异常
3. 通过无参构造创建对象

### 4. 实现getBean方法

- 判断bean定义map中是否有该beanName的key
    - 如果有
        - 如果是单例
            - 从容器中根据key(beanName)获取
                - 如果返回值不为null 返回bean
                - 如果为空，调用createBean方法 返回
        - 如果是多例
            - 创建bean并返回
    - 如果没有 抛出异常 `容器中没有该bean`

