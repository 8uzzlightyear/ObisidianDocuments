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

# 查询

## 多对一

```xml
<!--这种连表查询并没有查出teacher这个对象，而是将teacher表中name字段映射给Teacher类的name属性，  
因此查找结果teacher.id=0-->  
<!-- 连表查询，只查一次 -->
<resultMap id="StdTch" type="student">  
	<result property="id" column="id"/>  
	<result property="name" column="name"/>
	<!-- 这里的teacher查出来只有name属性，其他属性为默认值 -->
	<association property="teacher" javaType="Teacher">  
		<result property="name" column="tname"/>  
	</association>  
</resultMap>  
<select id="getStudent" resultType="student" resultMap="StdTch2">  
	select s.id id, s.name name, t.id tid from student s, teacher t where s.tid = t.id  
</select>  

<!-- 嵌套查询映射 -->
<resultMap id="StdTch2" type="student">  
	<result property="id" column="id"/>  
	<result property="name" column="name"/>
	<!-- 这里将teacher表中的所有属性都隐式的映射给了teacher的属性 --> 
	<association property="teacher" column="tid" javaType="teacher" select="getTeacher"/>  
</resultMap>  
<!--该查询语句可以不在接口中定义-->  
<select id="getTeacher" resultType="teacher">  
	select * from teacher where id = ${tid}  
</select>
```

## 一对多



```xml
<!--连表查询-->
<select id="getTeacher" resultMap="TeacherStudent">
	select s.id sid, s.name sname, t.name tname, t.id tid
	from teacher t, student s
	where s.tid = t.id and tid = ${tid}
</select>

<resultMap id="TeacherStudent" type="Teacher">
	<result column="tid" property="id"/>
	<result column="tname" property="name"/>
	<collection property="students" ofType="student">
		<result column="sid" property="id"/>
		<result column="sname"  property="name"/>
		<result column="tid" property="tid"/>
	</collection>
</resultMap>

<!--嵌套查询-->
<select id="getTeacher2" resultMap="TeacherStudent2">
	select  * from teacher where id = ${tid}
</select>

<resultMap id="TeacherStudent2" type="teacher">
	<!--这里需要映射两次“id”字段，如果把第一次映射省略的话，则结果的teacherId将会是默认值0-->
	<result column="id" property="id"/>
	<collection column="id" property="students" ofType="student" select="getStudentsBytid"/>
</resultMap>

<!--嵌套查询语句-->
<select id="getStudentsBytid" resultType="student">
	select * from student where tid = ${tid}
</select>
```