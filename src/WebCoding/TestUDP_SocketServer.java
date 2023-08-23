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
