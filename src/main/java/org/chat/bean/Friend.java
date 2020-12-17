package org.chat.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.chat.utils.MessageProcessing;

public class Friend {
    //    朋友备注信息
    public String friendName;
    //    聊天端口号
    public int chatPortNumber;
    //    聊天端口号
    public int filePortNumber;
    //    ip地址
    public String address;
    //    聊天记录
    private final ArrayList<String> chattingRecords;

    //    构造函数
    public Friend(String friendName, int chatPortNumber, int filePortNumber, String address) {
        this.friendName = friendName;
        this.chatPortNumber = chatPortNumber;
        this.filePortNumber = filePortNumber;
        this.address = address;
        chattingRecords = new ArrayList<>();
    }

    //    发单条消息
    public void sendMessage(String singleMessage) {
        try {
            //    自己发给朋友的消息加入消息记录
            addChattingRecords(singleMessage);
            //    合并包括自己ip地址的信息
            singleMessage = MessageProcessing.messageCombine(singleMessage);
            DatagramPacket sendPacket = new DatagramPacket(singleMessage.getBytes(), singleMessage.getBytes().length, InetAddress.getByName(address), chatPortNumber);
            new DatagramSocket().send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    发送文件
    public void sendFile(File file) {
        FileInputStream fileInputStream = null;
        try {//读取文件
            sendMessage("发送文件:" + file.getName());
            fileInputStream = new FileInputStream(file);
            byte[] byteFile = new byte[(int) file.length()];
            fileInputStream.read(byteFile);
            DatagramPacket sendPacket = new DatagramPacket(byteFile, byteFile.length, InetAddress.getByName(address), filePortNumber);
            new DatagramSocket().send(sendPacket);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("错误" + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println("错误" + e.getMessage());
                }
            }
        }

    }

    //    增加消息
    public void addChattingRecords(String singleMessage) {
        chattingRecords.add(singleMessage);
    }

    //    返回消息记录
    public ArrayList<String> exportChattingRecords() {
        return chattingRecords;
    }
}
