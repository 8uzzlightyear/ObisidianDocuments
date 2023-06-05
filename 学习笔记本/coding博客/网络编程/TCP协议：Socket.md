Socket类实例通过调用getInputStream和getOutputStream方法依靠I/O流进行发送和写入

# InetSocketAdress

[`InetAddress` ](./IP地址.md#my-anchor)和 `InetSocketAddress` 都是 Java 中与 IP 地址相关的类，但它们的作用和使用场景有所不同。

`InetAddress` 类代表***IP 地址***，可以是 IPv4 或 IPv6 地址。它可以通过 IP 地址的字符串表示形式（例如 "192.168.0.1"）或主机名（例如 "[www.example.com"）来创建实例。](https://bearly.ai/dashboard/notebook#)`InetAddress` 类提供了获取主机名、IP 地址、域名等信息的方法，也可以用于测试主机之间的网络连通性。 ^fd3a60

`InetSocketAddress` 类代表 ***IP 地址***和***端口号***的组合，通常用于开发网络应用程序。它可以通过 IP 地址和端口号创建实例。例如：


```java
InetSocketAddress address1 = new InetSocketAddress("localhost", 8080);  // 通过主机名和端口号创建
InetSocketAddress address2 = new InetSocketAddress("192.168.0.1", 8080);  // 通过 IP 地址和端口号创建
```

`InetSocketAddress` 对象可以用于建立网络连接、创建套接字绑定地址或服务器地址等操作。此外，`InetSocketAddress` 还提供了一些方法，例如 `getHostName()` 方法可以返回主机名，`getPort()` 方法可以返回端口号，还可以通过 `toString()` 方法将其转换为字符串表示形式。

***source：ChatGPT***

## 测试
```Java
package WebCoding;  
  
import java.net.InetSocketAddress;  
  
public class TestSocketAdress {  
public static void main(String[] args) {  
InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8080);  
System.out.println(inetSocketAddress);  
System.out.println(inetSocketAddress.getHostName());  
System.out.println(inetSocketAddress.getAddress());  
System.out.println(inetSocketAddress.getPort());  
}  
}
```
输出结果：
```
/127.0.0.1:8080
127.0.0.1
127.0.0.1/127.0.0.1
8080
```

# Socket

Socket 又称套接字，是计算机之间进行通信的一种机制，它使得应用程序可以通过网络协议（如TCP或UDP）在网络上进行数据传输。

在网络通信中，无论是客户端还是服务器端，都需要创建套接字，然后通过套接字来实现数据的传输和接收。套接字包含了一组网络地址和端口号，用于标识出一个网络连接的两端。

当一个应用程序建立了一个套接字连接时，它可以向套接字中写入数据，也可以从套接字中读取数据，从而实现与远程计算机进行数据交换和通信。

Socket 接口通常由操作系统提供，不同操作系统实现的Socket接口可能略有不同，但基本功能上是相同的。

一个完整的 Socket 通信程序应包含如下几个步骤。
	（1）创建 Socket 。
	（2）打开连接到 Socket 的输入/输出流。
	（3）按照一定的协议对 Socket 进行读/写操作。
	（4）关闭 Socket 。



# TCP协议的通信程序

## 教材示例

### 服务器

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class TestTCP_SocketServer {  
public static final int PORT = 8080;  
public static void main(String[] args) throws Exception {  
ServerSocket serverSocket = new ServerSocket(PORT); //ServerSocket 只需要一个端口号，不需要IP地址  
System.out.println("启动服务器：" + serverSocket);  
try {  
//先用serverSocket等待接收一个Socket  
Socket socket = serverSocket.accept();  
try {  
System.out.println("客户端建立" + socket);  
//创建一个输入流缓存阅读器, 缓存 InputStreamReader， 其能将字节流转化为字符流  
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
/*PrintWriter 是一个输出流，它允许您以文本方式将数据写入到文件或控制台等目标设备中。PrintWriter 对象可以连接到文件、字节数组、  
字符数组或另一个输出流上，它可以处理在输出数据时所需的各种转换（例如将整数、字符和数据类型转换为字符串）。*/  
/*PrintWriter包装BufferedWriter再包装OutputStreamWriter再包装一个输出流，PrintWriter 提供了一些方便的写入字符数据的方法，  
如 println() 和 printf()，它会自动进行字符编码转换*/  
PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);  
while (true) {  
String str = bufferedReader.readLine();  
if (str.equals("END")) break;  
printWriter.println("服务器回复：" + str);  
System.out.println(str);  
  
}  
} finally {  
System.out.println("关闭。。。");  
socket.close();  
}  
} finally {  
serverSocket.close();  
}  
}  
}
```
#### 注意事项
- 创建一个 PrintWriter 类对象时，构造函数的第二个参数必须选择 true 。否则在调用 println 方法时，不会将字符串输出至输出流而是继续缓存起来。也可以在打印一个字符串后用 flush() 方法，将该字符串输出。[详情请见](../IO流/IOReader&Writer.md#my-anchor)
- [Reader 类和Writer类](../IO流/IOReader&Writer#myanchor1)有其各自的作用
- 使用 try 语句能自动关闭不必要的资源（如IO流资源）
- 需要使用 finally 来关闭 Socket 和 ServerSocket 。
- Socket 和 ServerSocket 应该分层关闭，因为 Socket 是基于 ServerSocket 创建的，所有先关闭 Socket 。

### 客户端

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClient {  
public static void main(String[] args) throws IOException {  
//先获取IP地址  
InetAddress inetAddress = InetAddress.getByName("localhost");  
System.out.println("地址=" + inetAddress);  
//利用服务端地址和端口创建一个Socket  
Socket socket = new Socket(inetAddress, TestTCP_SocketServer.PORT);  
try {  
System.out.println("Socket =" + socket);  
//创建一个缓存阅读器  
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
//创建一个打印写入器，第二个参数的意义在于该对象在执行print等方法后将之前缓存的内容直接输出  
PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);  
for (int i = 0; i < 10; i++) {  
printWriter.println("客户端来信" + i);  
String s = bufferedReader.readLine();  
System.out.println(s);  
}  
printWriter.println("END");  
} finally {  
System.out.println("关闭");  
socket.close();  
}  
}  
}
```
#### 注意事项
- PrintWriter 第二个参数为 true 。

## 网络示例

### 服务端

#### 直接使用字节输入流接收输入流

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class TestTCP_SocketServerDemo2 {  
public static void main(String[] args) throws IOException {  
//定义所需参数  
ServerSocket serverSocket = null;  
Socket socket = null;  
InputStream is = null;  
OutputStream os = null;  
try {  
//实例化一个服务套接，只用一个端口参数  
serverSocket = new ServerSocket(9999);  
//用服务套接方法示例化一个套接  
socket = serverSocket.accept();  
System.out.println("套接已接收");  
//获取套接内的输入流  
is = socket.getInputStream();  
//创建一个字节数组输出流用于输出获取的输入流  
ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//创建一个字节数组用来读取输入流中的字节流，充当缓存  
byte[] bytes = new byte[1024];  
int len;  
while ((len = is.read(bytes)) != -1) {  
baos.write(bytes, 0, len);  
}  
System.out.println(baos.toString());  
} finally {  
socket.close();  
serverSocket.close();  
}  
}  
}
```

#### 使用BufferedReader接收输入流

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class TestTCP_SocketServerDemo3 {  
public static void main(String[] args) throws IOException {  
//定义所需参数  
ServerSocket serverSocket = null;  
Socket socket = null;  
try {  
//实例化一个服务套接，只用一个端口参数  
serverSocket = new ServerSocket(9999);  
//用服务套接方法示例化一个套接  
socket = serverSocket.accept();  
System.out.println("套接已接收");  
//获取套接内的输入流  
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
String s = bufferedReader.readLine();  
System.out.println(s);  
} finally {  
socket.close();  
serverSocket.close();  
}  
}  
}
```

### 客户端

#### 直接使用OutputSteam.write写入输出流

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClientDemo2 {  
public static void main(String[] args) throws IOException {  
//获取IP  
InetAddress inetAddress = InetAddress.getByName("localhost");  
Socket socket = new Socket(inetAddress, 9999);  
try {  
OutputStream os = socket.getOutputStream();  
os.write("人生苦短".getBytes());  
} finally {  
socket.close();  
}  
}  
}
```

#### 使用PrintWriter.println方法写入输出流

##### 有缓存

```Java
package WebCoding;  
  
import java.io.*;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClientDemo3 {  
public static void main(String[] args) throws IOException {  
//获取IP  
InetAddress inetAddress = InetAddress.getByName("localhost");  
Socket socket = new Socket(inetAddress, 9999);  
try {  
//第二个参数为true，否则不会写入至输出流  
PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);  
printWriter.println("hello");  
} finally {  
socket.close();  
}  
}  
}
```

##### 无缓存法

```Java
package WebCoding;  
  
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClientDemo6 {  
public static void main(String[] args) throws IOException {  
//获取IP  
InetAddress inetAddress = InetAddress.getByName("localhost");  
Socket socket = new Socket(inetAddress, 9999);  
try {  
//第二个参数为true，否则不会写入至输出流  
PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);  
printWriter.println("hello");  
} finally {  
socket.close();  
}  
}  
}
```

#### 使用BufferedWriter.write写入输出流

```Java
package WebCoding;  
  
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.OutputStreamWriter;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClientDemo4 {  
public static void main(String[] args) throws IOException {  
//获取Ip  
InetAddress inetAddress = InetAddress.getByName(null);  
//创建一个Socket  
Socket socket = new Socket(inetAddress, 9999);  
try {  
BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));  
bufferedWriter.write("It's Demo4");  
//使用BufferedWriter写入输出流必须在后面加flush方法  
bufferedWriter.flush();  
} finally {  
socket.close();  
}  
}  
}
```

#### 使用OutputStreamWriter.write写入输出流

```Java
package WebCoding;  
  
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.OutputStreamWriter;  
import java.net.InetAddress;  
import java.net.Socket;  
  
public class TestTCP_SocketClientDemo5 {  
public static void main(String[] args) throws IOException {  
//获取Ip  
InetAddress inetAddress = InetAddress.getByName(null);  
//创建一个Socket  
Socket socket = new Socket(inetAddress, 9999);  
try {  
OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());  
outputStreamWriter.write("It's Demo4");  
//使用BufferedWriter写入输出流必须在后面加flush方法  
outputStreamWriter.flush();  
} finally {  
socket.close();  
}  
}  
}
```

更多有关写入至输出流的细节请[查看](../IO流/OutputStream)。

### 注意事项及技巧

- 用`PrintWriter`和 `BufferedWriter`类型实例在写入数据时都需要及时[冲洗缓存](OutputStream#^30c160)，前者有构造参数自动冲洗，后者只能使用[`flush`方法](OutputStream#^159f7e)冲洗，否则数据不会写入输出流。

- 三种 Writer 中`OutputStreamWriter` 和`PrintWriter`可以输出字节流，因此这两个类型的示例对象都可以以`OutputStream`类型实例作为参数，由于`BufferedWriter`不能输出字节流，因此当需要使用缓存写入时，不可以使`BufferedWriter`实例直接写入`OutputStream`实例，应选择`OutputStreamWriter`实例作为`BufferedWriter`与`OutputStream`实例的中间转换器。

- 可以直接将输入流调用read方法保存至字节数组，字节数组可以通过作为创建一个字符串类的参数而转换为字符串。

```Java
byte[] buffer = new byte[1024]; 
int len = 0;
while ((len = is.read(bytes)) != -1){ 
String s = new String(buffer,0,len, "UTF-8"); //一个字符可能由多个字节构成（如中文字符），若输入流中的数据是使用多字节编码的，一个字节对应的不是一个字符，使用`String`的`String(byte[] bytes)`构造器或`new String(bytes)`会导致乱码。因此需要指定输入流中数据的编码方式。
System.out.println(s); 
}
```

 也可以创建一个字节输出流(ByteArrayOutputStream)，写入输出流后通过toString方法打印。
 
 ```Java
 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//创建一个字节数组用来读取输入流中的字节流，充当缓存  
byte[] bytes = new byte[1024];  
int len;  
while ((len = is.read(bytes)) != -1) {  
baos.write(bytes, 0, len);  
}  
System.out.println(baos.toString());
```

更多Socket实现方法请参考[这篇博客](https://www.cnblogs.com/gh110/p/15153670.html)