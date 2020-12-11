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
        user.getMessage((message)->{
            ProcessMessage.messageReceive(message,friends);
        });
        //    发送信息给朋友
        friends.get(friendIndex).sendMessage("赵力锐nb");

        //    获取聊天记录并遍历判断
        ArrayList<String> getChattingRecords = friends.get(friendIndex).getChattingRecords();
        for (String singleMessage : getChattingRecords) {
            if (ProcessMessage.isMyMessage(singleMessage))
                System.out.println("我发出消息：" + singleMessage);
            else
                System.out.println("我收到消息：" + ProcessMessage.getFriendMessage(singleMessage));
        }
    }
}
