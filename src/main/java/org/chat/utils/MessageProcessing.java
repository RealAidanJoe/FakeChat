package org.chat.utils;

import javafx.collections.ObservableList;
import org.chat.bean.Friend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MessageProcessing {
    private static final String COMBINE_SYMBOL = "!@#%&";
    public static final int NOT_ON_LIST = -1;

    //    信息合并
    public static String messageCombine(String message) {
        return getAddress() + COMBINE_SYMBOL + message;
    }

    //    寻找谁发的消息并更新消息记录
    public static int messageReceive(String message, ArrayList<Friend> friends) {
        String[] messageDetail = message.strip().split(COMBINE_SYMBOL);
        for (int friendIndex = 0; friendIndex < friends.size(); friendIndex++) {
            if (messageDetail[0].equals(friends.get(friendIndex).address)) {
                //    如果ip地址一样就是找到发给自己的朋友，将消息加入消息记录
                friends.get(friendIndex).addChattingRecords(message);
                return friendIndex;
            }
        }
        return NOT_ON_LIST;
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
        return message.strip().split(COMBINE_SYMBOL).length == 1;
    }

    public static String getFriendMessage(String message) {
        return message.strip().split(COMBINE_SYMBOL)[1];
    }
}
