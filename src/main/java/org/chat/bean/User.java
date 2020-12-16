package org.chat.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.net.*;

import static java.lang.Thread.sleep;

public class User {
    //    聊天端口号
    public int chatPortNumber;
    //    聊天端口号
    public int filePortNumber;
    //    数据块
    private final byte[] data = new byte[1024];
    //    文件保存路径
    public File fileSaveFolder;

    public User(int chatPortNumber, int filePortNumber, File fileSaveFolder) {
        this.chatPortNumber = chatPortNumber;
        this.filePortNumber = filePortNumber;
        this.fileSaveFolder = fileSaveFolder;
    }


    public void listenMessage(MessageListener messageListener) {
        Thread t = new Thread(() -> {
            try {
                DatagramPacket pack = new DatagramPacket(data, data.length);
                DatagramSocket socket = new DatagramSocket(chatPortNumber);
                while (true) {
                    socket.receive(pack);
                    String singleMessage = new String(pack.getData(), 0, pack.getLength());
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
                DatagramPacket pack = new DatagramPacket(data, data.length);
                while (true) {
                    DatagramSocket socket = new DatagramSocket(filePortNumber);
                    socket.receive(pack);
                    FileOutputStream fileWrite = null;
                    fileWrite = new FileOutputStream(fileSaveFolder);
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
