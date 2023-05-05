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

