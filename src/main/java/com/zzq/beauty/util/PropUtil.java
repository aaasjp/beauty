package com.zzq.beauty.util;

import java.io.*;
import java.util.*;

public class PropUtil {

    //根据key读取value
    public static String readValue(String filePath,String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty (key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //读取properties的全部信息
    public static Properties  readProperties(String filePath) throws IOException {
            Properties props = new Properties();
            InputStream in = new BufferedInputStream (new FileInputStream(filePath));
            props.load(in);
            return props;
    }

    //写入properties信息
    public static void writeProperties(String filePath,String parameterName,String parameterValue) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            //从输入流中读取属性列表（键和元素对）
            prop.load(fis);
            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty(parameterName, parameterValue);
            //以适合使用 load 方法加载到 Properties 表中的格式，
            //将此 Properties 表中的属性列表（键和元素对）写入输出流
            prop.store(fos, "Update '" + parameterName + "' value");
        } catch (IOException e) {
            System.err.println("Visit "+filePath+" for updating "+parameterName+" value error");
        }
    }
    public static void writePropertiesList(String filePath, HashMap<String,String> data) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            //从输入流中读取属性列表（键和元素对）
            prop.load(fis);
            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(filePath);
            for (Map.Entry<String, String> map:data.entrySet()){
                prop.setProperty(map.getKey(), map.getValue());
            }

            //以适合使用 load 方法加载到 Properties 表中的格式，
            //将此 Properties 表中的属性列表（键和元素对）写入输出流
            prop.store(fos, "Update  value");
        } catch (IOException e) {
            System.err.println("Visit "+filePath+" for updating  value error");
        }
    }
}
