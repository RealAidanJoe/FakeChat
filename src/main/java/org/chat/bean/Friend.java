package org.chat.bean;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.chat.utils.ProcessMessage;

public class Friend {
    //    朋友备注信息
    public String friendName;
    //    端口号
    public int portNumber;
    //    ip地址
    public String address;
    //    聊天记录
    private final ArrayList<String> chattingRecords;


    //    构造函数
    public Friend(String address, int portNumber, String friendName) {
        this.address = address;
        this.portNumber = portNumber;
        this.friendName = friendName;
        chattingRecords = new ArrayList<>();
    }

    //    发单条消息
    public void sendMessage(String singleMessage) {
        try {
            //    自己发给朋友的消息加入消息记录
            addChattingRecords(singleMessage);
            //    合并包括自己ip地址的信息
            singleMessage = ProcessMessage.messageCombine(singleMessage);
            DatagramPacket sendPacket = new DatagramPacket(singleMessage.getBytes(), singleMessage.getBytes().length, InetAddress.getByName(address), portNumber);
            new DatagramSocket().send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    增加消息
    public void addChattingRecords(String singleMessage) {
        chattingRecords.add(singleMessage);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendName='" + friendName + '\'' +
                ", portNumber=" + portNumber +
                ", address='" + address + '\'' +
                ", chattingRecords=" + chattingRecords +
                '}';
    }

    public ArrayList<String> getChattingRecords() {
        return chattingRecords;
    }
}
