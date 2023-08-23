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
