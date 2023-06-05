# 问题

## test测试缺少资源

如图所示

![image.png](https://raw.githubusercontent.com/8uzzlightyear/Image-host/main/images/20230603171106.png)

在 Maven 项目中，需要将资源文件打包到 build 中以便使用。可以在 `pom.xml` 文件中配置 `resources` 元素将资源文件打包到 build 中。

***并刷新MAVEN***

![image.png](https://raw.githubusercontent.com/8uzzlightyear/Image-host/main/images/20230603171448.png)


```XML
<build>
  <resources>
    <resource>
      <directory>src/main/java</directory>
      <includes>
        <include>**/*.properties</include>
        <include>**/*.xml</include>
      </includes>
      <filtering>true</filtering>
    </resource>
    <resource>
      <directory>src/main/resources</directory>
      <includes>
        <include>**/*.properties</include>
        <include>**/*.xml</include>
      </includes>
      <filtering>true</filtering>
    </resource>
  </resources>
</build>
```

## 空指针异常

在MybatisUtils类中`sqlSessionFactory`不能在try代码块中再申明一次
![image.png](https://raw.githubusercontent.com/8uzzlightyear/Image-host/main/images/20230603171746.png)

## 增删改失效

调用增删改方法后需要提交事务。

```Java  
@Test
    public void testGetUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count = mapper.deleteUser(5);
        sqlSession.commit();
        System.out.println(count);
        sqlSession.close();
    }
```

## 添加配置文件后找不到驱动类

![image.png](https://raw.githubusercontent.com/8uzzlightyear/Image-host/main/images/20230604165614.png)

### 配置文件db.properties内配置参数值不需要加引号
```properties
driver=com.mysql.cj.jdbc.Driver  
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&;useUnicode=true&;characterEncoding=UTF-8  
username=root  
password=123456
```

## 不使用url作为属性名（权宜解法）

```XML
<property name="driver" value="${driver}" />  
<property name="url" value="${URL}"/>  
<property name="username" value="${username}" />  
<property name="password" value="${password}" />
```

将 `url` 属性名改为`URL`之后，程序能够正常运行