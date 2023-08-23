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
