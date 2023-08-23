# 遇到的问题

## 1.项目的创建

一级项目使用maven创建，便于依赖的管理。二级项目（模块）使用maven-webapp创建，创建之后应刷新模块的pom.xml文件，确保模块的web文件左下角出现蓝点，否则web项目将部署失败。

## 2.前端jsp页面使用el表达式不能显示后端传递值

如使用${msg}表达式在前端页面不能将msg的值渲染出来

此时需要将maven-webapp创建项目时自带的web.xml配置文件替换为本地Tomcat的Root目录下的web.xml配置文件。即
```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" version="4.0" metadata-complete="true">
    <display-name>Welcome to Tomcat</display-name>
    <description> Welcome to Tomcat </description>
</web-app>
```
## 3.URL问题
`<url-pattern>/*</url-pattern>`:
表示包括.jsp页面等的所有内容，一般用在过滤器的映射上
`<url-pattern>/</url-pattern>:
表示不包括.jsp页面等**请求**内容，一般用在DispatcherServlet的映射上

## 4.Json字符串

### 1.Jackson
#### 导入依赖
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```
#### 前端乱码解决
```xml
    <!--
    支持mvc注解驱动
        在spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping注解生效
        必须向上下文中注册DefaultAnnotationHandlerMapping
        和一个AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理。
        而annotation-driven配置帮助我们自动完成上述两个实例的注入。
     -->
    <!--    解决后端发送前端的乱码问题-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```
#### 使用示例
```Java
@Controller
public class JsonController {
    @RequestMapping("/json1")
    //解决当前映射方法的乱码问题
    // @RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")
    @ResponseBody //使用该注解注解的方法不会走视图解析器
    public String json1() {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("junkwei", 3, "男");
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
}
```
### 2.fastjson
#### 导入依赖
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>
```
#### 使用示例
```Java
@Controller
public class AjaxController {
    @RequestMapping(value = "/a2", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String ajax2(){
        List<User> list = new ArrayList<User>();
        list.add(new User("秦疆1号",3,"男"));
        list.add(new User("秦疆2号",3,"男"));
        list.add(new User("秦疆3号",3,"男"));
        String str = JSON.toJSONString(list);
        return str; //由于@RestController注解，将list转成json格式返回
    }
}
```
#### 乱码问题解决
```Java
@RequestMapping(value = "/a2", produces = "application/json;charset=utf-8")
```

## 5.数据库无法连接
使用更高版本的mySql依赖
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
</dependency>
```
同时将属性文件改为
`jdbc.driver=com.mysql.cj.jdbc.Driver`
