package org.chat.bean;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javafx.scene.Scene;
import org.chat.utils.ProcessMessage;

public class main {
    public static void main(String[] args) {
        //    初始化用户
        User user = new User(2077);
        //    初始化打开的朋友序号
        int friendIndex = 0;
        //    初始化朋友列表
        ArrayList<Friend> friends = new ArrayList<>();
        //    添加朋友（第一个参数为ip地址，现在为本机通讯）
        friends.add(new Friend(ProcessMessage.getAddress(), 2077, "aidan joe"));

        //    监听获得信息并自动更新
        user.getMessage((message) -> {
            int sourceFriendIndex = ProcessMessage.messageReceive(message, friends);
            if (sourceFriendIndex == ProcessMessage.NOT_ON_LIST)
                System.out.println("NOT_ON_LIST");
            else if (friendIndex == sourceFriendIndex)
                System.out.println("现在打开框更新的信息：" + ProcessMessage.getFriendMessage(message));
        });
        //    发送信息给朋友
        friends.get(friendIndex).sendMessage("赵力锐nb");

        System.out.println(friends.get(friendIndex).getChattingRecords());
        //    获取聊天记录并遍历判断
        ArrayList<String> getChattingRecords = friends.get(friendIndex).getChattingRecords();
        for (int i = 0; i < getChattingRecords.size(); i++) {
            if (ProcessMessage.isMyMessage(getChattingRecords.get(i)))
                System.out.println("我发出消息：" + getChattingRecords.get(i));
            else
                System.out.println("我收到消息：" + ProcessMessage.getFriendMessage(getChattingRecords.get(i)));
        }
        System.out.println(friends.get(friendIndex).getChattingRecords());

    }
}
