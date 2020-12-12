package org.chat.bean;

import java.util.ArrayList;

import org.chat.utils.ImportExportProcessing;
import org.chat.utils.MessageProcessing;

public class main {
    public static void main(String[] args) {
        //    初始化用户
        User user = new User();
        //    初始化打开的朋友序号
        int friendIndex = 0;
        //    初始化朋友列表
        ArrayList<Friend> friends = new ArrayList<>();
        //    添加朋友（第一个参数为ip地址，现在为本机通讯）
        friends.add(new Friend(MessageProcessing.getAddress(), "aidan joe"));

        //    监听获得信息并自动更新
        user.listenMessage((message) -> {
            int sourceFriendIndex = MessageProcessing.messageReceive(message, friends);
            if (sourceFriendIndex == MessageProcessing.NOT_ON_LIST)
                System.out.println("NOT_ON_LIST");
            else if (friendIndex == sourceFriendIndex)
                System.out.println("现在打开框更新的信息：" + MessageProcessing.getFriendMessage(message));
        });
        //    发送信息给朋友
        friends.get(friendIndex).sendMessage("赵力锐nb");

        System.out.println(friends.get(friendIndex).exportChattingRecords());
        //    获取聊天记录并遍历判断
        ArrayList<String> getChattingRecords = friends.get(friendIndex).exportChattingRecords();
        for (int i = 0; i < getChattingRecords.size(); i++) {
            if (MessageProcessing.isMyMessage(getChattingRecords.get(i)))
                System.out.println("我发出消息：" + getChattingRecords.get(i));
            else
                System.out.println("我收到消息：" + MessageProcessing.getFriendMessage(getChattingRecords.get(i)));
        }
    }
}
