package com.example.smallfish.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.smallfish.model.BottomBar;
import com.example.smallfish.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 解析destination.json，将destination.json解析成HashMap
 *
 * @author iwen大大怪
 * Create to 2020/10/14 15:39
 */
public class AppConfig {

    private static HashMap<String, Destination> sDestConfig;
    private static BottomBar sBottomBar;

    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = parseFile("destination.json");
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            }.getType());
        }
        return sDestConfig;
    }

    public static BottomBar getBottomBar() {
        if (sBottomBar == null) {
            String content = parseFile("main_tabs_config.json");
            sBottomBar = JSON.parseObject(content, BottomBar.class);
        }
        return sBottomBar;
    }

    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getResources().getAssets();

        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            stream = assets.open(fileName);
            // 开始解析文件输入流
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            // 将读到的每一行数据赋值给line字段
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stream != null){
                    stream.close();
                }

                if (reader != null){
                    reader.close();
                }
            }catch (Exception e){

            }
        }
        return builder.toString();
    }
}
