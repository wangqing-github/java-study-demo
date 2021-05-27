package wq.study.demo.other.udpDemo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPReceiveTest {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(12332);
        byte[] bytes = new byte[1024];
        int length = bytes.length;
        DatagramPacket dp = new DatagramPacket(bytes, length);
        datagramSocket.receive(dp);
        InetAddress inetAddress = dp.getAddress();
        byte[] receive = dp.getData();
        int len = dp.getLength();
        String receiveData = new String(receive, 0, len, StandardCharsets.UTF_8);
        System.out.println(inetAddress);
        System.out.println(receiveData);
    }
}
