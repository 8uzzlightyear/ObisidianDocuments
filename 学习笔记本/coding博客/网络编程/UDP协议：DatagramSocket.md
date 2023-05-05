DatagramSocket类实例调用send和receive方法发送和接收DatagramPacket数据包

# UDP协议的通信程序

使用 UDP 协议时，在客户和服务器程序都可以放置一个 DatagramSocket( 数据报套接字)，但与 ServerSocket 不同，前者不会等待建立一个连接的请求, 对数据报来说，它的数据包必须知道自己来自何处，以及打算去哪里。DatagramSocket 用 于收发数据包，而DatagramPacket 包舎了具体的信息。准各接收一个数据报时、只需提供一个缓冲区,以便安置接收到的数据。数据包抵达时，通过 DatagramSocket，作为信息起源地的因特网地址以及端口编号会自动得到。

## 教材示例

### 服务端

```Java
package WebCoding;  
  
import java.io.IOException;  
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.SocketException;  
  
public class TestUDP_SocketServer {  
//先初始化类属性  
static final int INPORT = 1711;  
private byte[] buf = new byte[1024]; //创建一个用来存数据包数据的字节数组  
private DatagramPacket dp = new DatagramPacket(buf, buf.length); //数据包会接收到数据并写入buf中  
//构造方法  
public TestUDP_SocketServer() {  
try {  
//创建一个套接  
DatagramSocket socket = new DatagramSocket(INPORT);  
System.out.println("启动服务器");  
while (true) {  
//套接接收数据包传给dp  
socket.receive(dp);  
//读取数据包dp中的数据  
String rcvd = new String(dp.getData());// + ", from:" + dp.getAddress() + ", 端口:" + dp.getPort();  
System.out.println(rcvd);  
//回复客户端  
String echoString = "回写： " + rcvd;  
//新建一个缓存字节数组  
byte[] buf = echoString.getBytes();  
//新建一个回复数据包，将缓存字节数据写入数据包  
DatagramPacket echodp = new DatagramPacket(buf, buf.length, dp.getAddress(), dp.getPort());  
socket.send(echodp);  
System.out.println("回复以发送");  
}  
  
} catch (SocketException e) {  
throw new RuntimeException(e);  
} catch (IOException e) {  
throw new RuntimeException(e);  
}  
}  
public static void main(String[] args) {  
new TestUDP_SocketServer();  
}  
}
```

### 客户端

```Java
package WebCoding;  
  
import java.io.IOException;  
import java.net.*;  
  
public class TestUDP_SocketClient extends Thread {  
//类属性  
private InetAddress inetAddress;  
private byte[] buf = new byte[1000]; //用来缓存数据包数据  
private DatagramSocket socket; //用来发送和接收数据包  
private int id;  
private DatagramPacket dp = new DatagramPacket(buf, buf.length); //该数据包用来读取数据,该数据包一定要有一个字节数组来实例化，否则接收不了数据  
public TestUDP_SocketClient(int identifier) {  
id = identifier;  
try {  
socket = new DatagramSocket();  
inetAddress = InetAddress.getByName("localhost");  
} catch (SocketException e) {  
throw new RuntimeException(e);  
} catch (UnknownHostException e) {  
throw new RuntimeException(e);  
}  
System.out.println("客户端启动");  
}  
  
public void run() {  
try {  
for (int i = 0; i < 5; i++) {  
String outMessage = "客户端 # " + id + ", 消息 #" + i;  
buf = outMessage.getBytes();  
socket.send(new DatagramPacket(buf, buf.length, inetAddress, TestUDP_SocketServer.INPORT));  
System.out.println("消息已发送");  
  
socket.receive(dp);  
String rcvd = "客户端 #" + id + "，接收" + dp.getAddress() + dp.getPort() + ":" + new String(dp.getData());  
System.out.println(rcvd);  
}  
} catch (IOException e) {  
throw new RuntimeException(e);  
}  
}  
  
public static void main(String[] args) {  
for (int i = 0; i < 10; i++) {  
new TestUDP_SocketClient(i).run();  
}  
}  
}
```

### 注意事项及技巧

- 创建DatagramPacket实例时一定要用一个字节数组实例来储存数据。DatagramPacket包装的也是字节数据。
- **发送数据的方法**
```Java
String outMessage = "客户端 # " + id + ", 消息 #" + i;  
buf = outMessage.getBytes();  
socket.send(new DatagramPacket(buf, buf.length, inetAddress, TestUDP_SocketServer.INPORT)); 
```
 先将字符串转换为字节数组，再创建一个匿名DatagramPacket实例将字节数组打包，最后再通过DatagramSocket实例send方法发送。
- **接收数据的方法**
```Java
socket.receive(dp); //dp为已用一个字节数组做缓存的DatagramPacket实例
String rcvd = new String(dp.getData());//dp.getData()等到的是byte类型数据
```

更多DatagramSocket实现方法请参考[这篇博客](https://www.cnblogs.com/gh110/p/15153670.html)
