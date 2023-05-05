<a name="my-anchor"></a>
# 实例化

```Java
package WebCoding;  
  
import java.net.InetAddress;  
import java.net.UnknownHostException;  
  
public class TestGetIP {  
public static void main(String[] args) throws UnknownHostException {  
//查询本机地址  
InetAddress inetAddress = InetAddress.getByName("127.0.0.1");  
System.out.println(inetAddress);  
InetAddress inetAddress2 = InetAddress.getByName("localhost");  
System.out.println(inetAddress2);  
  
//查询网站IP地址  
InetAddress inetAddress3 = InetAddress.getByName("www.taobao.com");  
System.out.println(inetAddress3);  
  
}  
}
```
 输出结果：
```
/127.0.0.1
localhost/127.0.0.1
www.taobao.com/27.221.123.166
```

# 方法


InetAddress的实例还有一些方法，可以返回需要的信息

```Java
// 常用方法 
// System.out.println(inetAddress.getAddress()); // 返回的是一个字节数组 无用 
System.out.println(inetAddress.getCanonicalHostName()); 
// 规范的名字 
System.out.println(inetAddress.getHostAddress()); // IP 
System.out.println(inetAddress.getHostName()); // 域名，或者自己电脑的名字
```
