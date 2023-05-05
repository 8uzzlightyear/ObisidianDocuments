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
