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
