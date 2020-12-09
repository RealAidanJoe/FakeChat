package org.chat.bean;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class User {
    //    端口号
    private int portNumber;
    //    UDP接收对象
    private DatagramSocket socket;
    //    数据块
    byte data[] = new byte[1024];


    public User(int portNumber) {
        this.portNumber = portNumber;
        try {
            socket = new DatagramSocket(portNumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getMessage() {
        Thread t=new Thread(()->{
            while (true){
                System.out.println("hello");
            }
        });
        t.start();
    }

}
