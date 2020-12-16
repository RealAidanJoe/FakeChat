package org.chat.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.chat.bean.Friend;
import org.chat.bean.User;


public class main {
    public static void main(String[] args) {
        //    初始化朋友列表
//        ArrayList<Friend> friends = new ArrayList<>();
//        friends.add(new Friend("AidanJoe", 2077, 2088, MessageProcessing.getAddress()));
        //    添加朋友（第一个参数为ip地址，现在为本机通讯）
//        friends.add(new Friend(MessageProcessing.getAddress(), "aidan joe"));
//        friends.add(new Friend(MessageProcessing.getAddress(), "zeng"));
//        friends.add(new Friend(MessageProcessing.getAddress(), "friendA"));
//        friends.add(new Friend(MessageProcessing.getAddress(), "friendB"));

//        ImportExportProcessing.exportFriends(friends);

//        ArrayList<Friend> friends = ImportExportProcessing.importFriends();
//        System.out.println(friends.get(0).toString());

        User user = null;
        try {
            user = new User(2077, 2088,new File("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImportExportProcessing.exportUser(user);

    }
}
