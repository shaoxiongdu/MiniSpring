# 实现一个简单的迷你Spring


## 一. 项目结构

```text
.
└── com
    └── lixiang
        ├── Application.java                            //测试
        ├── service                                     //业务
        │   ├── UserController.java
        │   ├── UserDao.java
        │   ├── UserService.java
        │   ├── UserServiceInterface.java
        │   └── XiongBeanPostProcessorImpl.java
        └── spring                                      //Spring核心
            ├── BeanDefinition.java                     //Bean定义类
            ├── Utils.java                              //工具类
            ├── annotation                              //注解包
            │   ├── Autowired.java                //自动注入注解
            │   ├── Component.java                //bean注解
            │   ├── ComponentScan.java            //包扫描路径注解
            │   └── Scope.java                    //单例多例注解
            ├── config                                  //配置包
            │   ├── XiongApplicationConfig.java   //配置接口
            │   └── impl                          //实现
            │       ├── ConfigurationImpl.java    //配置类实现
            │       └── XmlImpl.java              //读取XML实现
            ├── context                                 //容器包
            │   ├── XiongContext.java             //容器接口
            │   └── impl                          //容器实现包    
            │       └── XiongApplicationContext.java    //主要实现类
            ├── enums                                   //枚举包
            │   └── ScopeEnum.java                //单例多例枚举
            └── interfaces                              //接口包
                ├── BeanNameAware.java                  //设置bean名称前置接口
                ├── BeanPostProcessor.java              //前置后置处理器接口
                └── InitializingBean.java               //初始化接口

```

## 二. 容器接口

```java
/**
 * 最基础的容器
 *
 * @author dushaoxiong@lixiang.com
 * @version 1.0
 * @date 2022/3/17 21:16
 */
public interface XiongContext {

    /**
     * 通过名称获取bean
     * @param beanName
     * @return
     */
    Object getBeanByName(String beanName);

    /**
     * 通过类型获取bean
     * @param clazz
     * @return
     */
    Object getBeanByClass(Class clazz);

    /**
     * 获取所有的bean名称
     * @return
     */
    List<String> getAllBeanName();
}
```

## 三. 容器实现

- 主要的属性：

```java
    /**
     * 配置类
     */
    private final Class configClazz;

    /**
     * bean定义map  key bean名称  value bean定义类
     */
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 单例池 存放容器中所有的单例bean  key bean名称 value bean对象
     */
    private ConcurrentHashMap<String, Object> singletonPool = new ConcurrentHashMap<>();

    /**
     * 实现了该接口的所有bean集合
     */
    private ArrayList<BeanPostProcessor> beanPostProcessorArrayList = new ArrayList<>();
```

### 3.1. 实现构造方法

1. 从配置类中读取ComponentScan的值 扫描该路径
2. 通过反射循环创建该路径下的所有Java类

   1. 判断是否有Component注解。
      - 如果有
        1. 则创建对应的bean定义对象
        2. 从注解中获取bean名称,bean类型及是否是单例，bean类型等。
           1. 添加到bean定义map中
        3. 判断该bean是否实现了BeanPostProcessor接口
           1. 如果实现了，通过反射创建对应的实例，并添加到对应的list(beanPostProcessorList)中。

3. 创建bean定义map中的所有单例bean
   1. 循环map
      - 如果当前是单例bean

          1. 调用创建bean的方法

          2. 放入单例池 `singletonPool`中

### 3.2. 实现createBean(String beanName,BeanDifinition beanDefinition)方法

![image-20220326210028074](https://images-1301128659.cos.ap-beijing.myqcloud.com/image-20220326210028074.png)

### 3.3. 实现getBean(String beanName)方法

- 判断bean定义map中是否有该beanName的key
    - 如果有
        - 如果是单例
            - 从容器中根据key(beanName)获取
                - 如果返回值不为null 返回bean
                - 如果为空，调用createBean方法 返回
        - 如果是多例
            - 创建bean并返回
    - 如果没有 抛出异常 `容器中没有该bean`

### 3.4. 实现getBean(Class clazz)方法

- 循环bean定义map
  - 如果value(beanDefiniation)中的clazz和参数相同
    - 获取当前元素的key 即为beanName
      - 通过getBean(String beanName)获取bean并返回
  - 如果不同 结束此次循环
- 循环结束，抛出 容器中没有该类型的bean异常

### 3.5. 实现getAllBeanName()方法

- 将bean定义map的key转为list返回即可

## 4、更多开源项目

> 每天都会分享一些好玩，有趣，又沙雕的开源项目。或者是比较实用的开发工具。![Github推荐](https://gitee.com/ShaoxiongDu/imageBed/raw/master/image-20210820144130666.png)

## 5、反馈及改进

欢迎提出`issues`,看到就会回馈.并且将您添加到项目贡献者列表中。

## 6、参与贡献（非常欢迎！）

> 手动打字难免会有错别字，如果您在学习过程中发现了错别字或者需要补充及修正的知识点。
>
> 欢迎及时修正本项目，让我们一起为开源做贡献！ 

具体步骤如下:

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request，填写必要信息。
5. 等待审核即可。通过之后会邮件通知您。

## 7、许可证

在 MIT 许可下分发。有关更多信息，请参阅[`LICENSE`](./LICENSE)。
