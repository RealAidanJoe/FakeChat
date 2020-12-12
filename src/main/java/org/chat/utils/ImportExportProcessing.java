package org.chat.utils;

import java.io.*;
import java.util.ArrayList;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.chat.bean.Friend;
import org.chat.bean.Constant;


public class ImportExportProcessing {
    public static void exportFriends(ArrayList<Friend> friends) {
        try {
            File file = new File(Constant.SAVE_FRIENDS_PATH);
            //如果没有文件就创建
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("friends.txt"));
            for (Friend friend : friends) {
                writer.write(JSON.toJSONString(friend) + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Friend> importFriends() {
        try {
            File file = new File(Constant.SAVE_FRIENDS_PATH);
            //如果没有文件就创建
            if (!file.isFile()) {
                return null;
            }
            ArrayList<Friend> friends = new ArrayList<>();
            //BufferedReader是可以按行读取文件
            FileInputStream inputStream = null;
            inputStream = new FileInputStream(Constant.SAVE_FRIENDS_PATH);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                friends.add(JSON.parseObject(str, Friend.class));
            }
            //close
            inputStream.close();
            bufferedReader.close();
            return friends;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
