package org.chat.controller;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Home {
    byte data[];

    public Home() {
        byte data[] = "shabi".getBytes();
    }

    public Button send;

    public void sendClicked(MouseEvent mouseEvent) {
        try {
            InetAddress address = InetAddress.getByName("192.168.0.3");
            // 构建对方address为192.168.0.3
            DatagramPacket SendPacket = new DatagramPacket(data, data.length, address, 2013);
            //构建待发送UDP数据包
            DatagramSocket Post = new DatagramSocket();
            //创建UDP发送对象
            Post.send(SendPacket);              //发送数据包
        } catch (Exception e1) {
        }

    }
}
