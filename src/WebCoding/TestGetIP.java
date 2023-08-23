package WebCoding;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestGetIP {
    public static void main(String[] args) throws UnknownHostException {
        //查询本机地址
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
        System.out.println(inetAddress);
        InetAddress inetAddress2 = InetAddress.getByName("localhost");
        System.out.println(inetAddress2);

        //查询网站IP地址
        InetAddress inetAddress3 = InetAddress.getByName("www.taobao.com");
        System.out.println(inetAddress3);

        System.out.println(inetAddress.getHostName());
    }
}
