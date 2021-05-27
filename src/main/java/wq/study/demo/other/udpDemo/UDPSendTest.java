package wq.study.demo.other.udpDemo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSendTest {
    //先运行接收
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket();
        String string = "my name is send";
        byte[] bytes = string.getBytes();
        int length = string.length();
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
        int port = 12332;
        DatagramPacket datagramPacket = new DatagramPacket(bytes, length, inetAddress, port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }
}
