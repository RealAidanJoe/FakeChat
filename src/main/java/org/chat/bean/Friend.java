package org.chat.bean;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Friend {
    //    朋友备注信息
    private String friendName;
    //    端口号
    private int portNumber;
    //    ip地址
    private InetAddress inetAddress;
    //    聊天记录
    ArrayList<String> chattingRecords;


    //    构造函数
    public Friend(String address, int portNumber, String friendName) {
        try {
            this.inetAddress = InetAddress.getByName(address);
            this.portNumber = portNumber;
            this.friendName = friendName;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //    发单条消息
    public void sendMessage(String singleMessage) {
        DatagramPacket sendPacket = new DatagramPacket(singleMessage.getBytes(), singleMessage.getBytes().length, inetAddress, portNumber);
        try {
            new DatagramSocket().send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendName='" + friendName + '\'' +
                ", portNumber=" + portNumber +
                ", inetAddress=" + inetAddress +
                '}';
    }

}
