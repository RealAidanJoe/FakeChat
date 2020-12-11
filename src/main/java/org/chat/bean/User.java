package org.chat.bean;

import java.net.*;

import static java.lang.Thread.sleep;

public class User {
    //    端口号
    private int portNumber;
    //    UDP接收对象
    private DatagramSocket socket;
    //    数据块
    byte data[] = new byte[1024];
    //    监听
    private MessageListener messageListener;


    public User(int portNumber) {
        this.portNumber = portNumber;
        try {
            socket = new DatagramSocket(portNumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    //    获取自己的端口号
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
        Thread t = new Thread(() -> {
            try {
                DatagramPacket pack = new DatagramPacket(data, data.length);
                while (true) {
                    socket.receive(pack);
                    String singleMessage = new String(pack.getData(), 0, pack.getLength());
                    System.out.println(singleMessage);
                    messageListener.getMessage(singleMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    //注册监听器，该类没有监听器对象啊，那么就传递进来吧。
    public void registerLister(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

}
