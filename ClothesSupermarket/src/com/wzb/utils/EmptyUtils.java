package com.wzb.utils;

public class EmptyUtils {
    public static boolean isEmpty(String s){
        if(s == null||s.equals("")){
            return true;
        }else {
            return false;
        }
    }
}
