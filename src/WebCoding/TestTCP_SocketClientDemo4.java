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
