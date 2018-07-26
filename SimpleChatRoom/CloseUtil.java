package com.wzb.tcp.chat.demo01;
/**
 * 工具类，用于关闭打开的io流
 */

import java.io.Closeable;
public class CloseUtil {
    public static void closeAll(Closeable... io){
        for(Closeable temp:io){
            try {
                if(temp!=null){
                    temp.close();
                }
            }catch (Exception e){

            }
        }
    }
}