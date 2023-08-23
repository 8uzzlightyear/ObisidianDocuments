package WebCoding;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestTCP_SocketServer {
    public static final int PORT = 8080;
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT); //ServerSocket 只需要一个端口号，不需要IP地址
        System.out.println("启动服务器：" + serverSocket);
        try {
            //先用serverSocket等待接收一个Socket
            Socket socket = serverSocket.accept();
         try {
            System.out.println("客户端建立" + socket);
            //创建一个输入流缓存阅读器, 缓存 InputStreamReader， 其能将字节流转化为字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*PrintWriter 是一个输出流，它允许您以文本方式将数据写入到文件或控制台等目标设备中。PrintWriter 对象可以连接到文件、字节数组、
            字符数组或另一个输出流上，它可以处理在输出数据时所需的各种转换（例如将整数、字符和数据类型转换为字符串）。*/
            /*PrintWriter包装BufferedWriter再包装OutputStreamWriter再包装一个输出流，PrintWriter 提供了一些方便的写入字符数据的方法，
            如 println() 和 printf()，它会自动进行字符编码转换*/
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            while (true) {
                String str = bufferedReader.readLine();
                if (str.equals("END")) break;
                printWriter.println("服务器回复：" + str);
                System.out.println(str);

            }
        } finally {
             System.out.println("关闭。。。");
             socket.close();
         }
        } finally {
            serverSocket.close();
        }
    }
}

