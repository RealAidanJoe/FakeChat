package org.chat.utils;

import java.io.*;
import java.util.ArrayList;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javafx.collections.ObservableList;
import org.chat.bean.Friend;
import org.chat.bean.Constant;
import org.chat.bean.User;


public class ImportExportProcessing {
    public static void exportFriends(ObservableList<Friend> friends) {
        try {
            File file = new File(Constant.SAVE_FRIENDS_PATH);
            //如果没有文件就创建
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter(Constant.SAVE_FRIENDS_PATH));
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
                return new ArrayList<>();
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
        return new ArrayList<>();
    }

    public static void exportUser(User user) {
        try {
            File file = new File(Constant.SAVE_USER_PATH);
            //如果没有文件就创建
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter(Constant.SAVE_USER_PATH));
            writer.write(JSON.toJSONString(user));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User importUser() {
        try {

            File file = new File(Constant.SAVE_USER_PATH);
            //如果没有文件就创建
            if (!file.isFile()) {
                return null;
            }
            ArrayList<Friend> friends = new ArrayList<>();
            User user = null;
            //BufferedReader是可以按行读取文件
            FileInputStream inputStream = null;
            inputStream = new FileInputStream(Constant.SAVE_USER_PATH);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                user=JSON.parseObject(str,User.class);
            }
            //close
            inputStream.close();
            bufferedReader.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
