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
