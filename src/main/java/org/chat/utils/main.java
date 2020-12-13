package org.chat.utils;

import java.util.ArrayList;

import org.chat.bean.Friend;


public class main {
    public static void main(String[] args) {
        //    初始化朋友列表
        ArrayList<Friend> friends = new ArrayList<>();
        //    添加朋友（第一个参数为ip地址，现在为本机通讯）
        friends.add(new Friend(MessageProcessing.getAddress(), "aidan joe"));
        friends.add(new Friend(MessageProcessing.getAddress(), "zeng"));
        friends.add(new Friend(MessageProcessing.getAddress(), "friendA"));
        friends.add(new Friend(MessageProcessing.getAddress(), "friendB"));

        ImportExportProcessing.exportFriends(friends);

//        ArrayList<Friend> friends = ImportExportProcessing.importFriends();
//        System.out.println(friends);
    }
}
