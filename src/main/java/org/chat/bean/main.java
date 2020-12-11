package org.chat.bean;

import com.alibaba.fastjson.JSON;

public class main {
    public static void main(String[] args) {
//        final String[] s = new String[1];
//        User user = new User(2077);
//        user.registerLister(new MessageListener() {
//            @Override
//            public void getMessage(String singleMessage) {
//                System.out.println(singleMessage);
//                s[0] = singleMessage;
//                System.out.println(s[0]);
//            }
//        });
//        Friend friend = new Friend(user.getAddress(), 2077, "aidan joe");
//        user.getMessage(); //暂时输出接收的消息
//        friend.sendMessage("赵力锐nb");
        Test test = new Test();
        System.out.println(JSON.toJSONString(test));
//        System.out.println(JSON.toJSONString(test));
    }
}
