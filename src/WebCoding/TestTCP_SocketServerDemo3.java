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
