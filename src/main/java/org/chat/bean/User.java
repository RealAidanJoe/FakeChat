package org.chat.bean;

import javafx.beans.property.SimpleIntegerProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.net.*;

import static java.lang.Thread.sleep;

import org.chat.utils.MessageProcessing;

public class User {
    //    聊天端口号
//    public int chatPortNumber;
    public int chatPortNumber;
    //    聊天端口号
//    public int filePortNumber;
    public int filePortNumber;
    //    数据块
    private final byte[] data = new byte[1024];
    //    文件保存路径
    public File fileSaveFolder;
    //    获取上一个文件的文件名称
    public String fileName;

    public User(int chatPortNumber, int filePortNumber, File fileSaveFolder) {
        this.chatPortNumber = chatPortNumber;
        this.filePortNumber = filePortNumber;
        this.fileSaveFolder = fileSaveFolder;
//        this.chatPortNumber = new SimpleIntegerProperty(chatPortNumber);
//        this.filePortNumber = new SimpleIntegerProperty(filePortNumber);
    }


    public void listenMessage(MessageListener messageListener) {
        Thread t = new Thread(() -> {
            try {
                DatagramSocket socket;
                socket = new DatagramSocket(chatPortNumber);
                String fileMsg;
                DatagramPacket pack = new DatagramPacket(data, data.length);
                while (true) {
                    socket.receive(pack);
                    String singleMessage = new String(pack.getData(), 0, pack.getLength());
                    System.out.println(MessageProcessing.getFriendMessage(singleMessage));
                    System.out.println(singleMessage);
                    if (MessageProcessing.isFileMessage(fileMsg = MessageProcessing.getFriendMessage(singleMessage)))
                        fileName = MessageProcessing.getFileName(fileMsg);

                    messageListener.getMessage(singleMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void listenFile(FileListener fileListener) {
        Thread t = new Thread(() -> {
            try {
                DatagramSocket socket;
                socket = new DatagramSocket(filePortNumber);
                DatagramPacket pack = new DatagramPacket(data, data.length);
                while (true) {
                    socket.receive(pack);
                    FileOutputStream fileWrite = null;
                    sleep(1000);
                    System.out.println(fileName);
                    fileWrite = new FileOutputStream(fileSaveFolder + "/" + fileName);
                    fileWrite.write(pack.getData());
                    fileWrite.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
