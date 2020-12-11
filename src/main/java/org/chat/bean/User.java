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


    public User(int portNumber) {
        this.portNumber = portNumber;
        try {
            socket = new DatagramSocket(portNumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void getMessage(MessageListener messageListener) {
        Thread t = new Thread(() -> {
            try {
                DatagramPacket pack = new DatagramPacket(data, data.length);
                while (true) {
                    socket.receive(pack);
                    String singleMessage = new String(pack.getData(), 0, pack.getLength());
                    messageListener.getMessage(singleMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

}
