package org.chat.utils;

import org.chat.bean.Friend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ProcessMessage {
    private static final String CombineSymbol = "!@#%&";

    //    信息合并
    public static String messageCombine(String message) {
        return getAddress() + CombineSymbol + message;
    }

    //    寻找谁发的消息并更新消息记录
    public static void messageReceive(String message, ArrayList<Friend> friends) {
        String[] messageDetail = message.strip().split(CombineSymbol);
        for (Friend friend : friends) {
            if (messageDetail[0].equals(friend.address)) {
                //    如果ip地址一样就是找到发给自己的朋友，将消息加入消息记录
                friend.addChattingRecords(message);
                break;
            }
        }
    }

    //    获取本机的ip地址
    public static String getAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean isMyMessage(String message) {
        //    如果没有合并符号就是自己的消息了
        return message.strip().split(CombineSymbol).length == 1;
    }

    public static String getFriendMessage(String message) {
        return message.strip().split(CombineSymbol)[1];
    }
}
