package com.wzb.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.*;

/**
 * 封装request
 */
public class Request {
    //请求方式
    private String method;
    private String url;
    private Map<String, List<String>> parameterMapValues;
    public static final String CRLF = "\r\n";
    private InputStream is;
    private String requestInfo;
    public Request(){
        method = "";
        url = "";
        parameterMapValues = new HashMap<String, List<String>>();
        requestInfo = "";
    }
    public Request(InputStream is){
        this();
        this.is = is;
        byte[] data = new byte[20480];
        int len = 0;
        try {
            len = is.read(data);
            requestInfo = new String(data,0,len);
        } catch (IOException e) {
            return;
        }
        //分析请求信息
        parseRequestInfo();
    }

    /**
     * 分析请求信息
     */
    private void parseRequestInfo(){
        if(requestInfo==null||(requestInfo=requestInfo.trim()).equals("")){
            return;
        }
        /**======================================================
         * 从信息的首行分解出：请求方式 请求路径 请求参数（get可能存在）
         * ======================================================
         */
        String paramString = "";//接收请求参数
        //1、获取请求方式
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
        int idx = requestInfo.indexOf("/");// / 的位置
        this.method = firstLine.substring(0,idx).trim();
        String urlStr = firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();
        if(this.method.equalsIgnoreCase("post")){
            this.url = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if (this.method.equalsIgnoreCase("get")){
            if(urlStr.contains("?")){//是否存在参数
                String[] urlArray = urlStr.split("\\?");
                this.url = urlArray[0];
                paramString = urlArray[1];//接收请求参数
            }else {
                this.url = urlStr;
            }
        }
        //2、将请求参数封装到Map中
        //不存在请求参数
        if(paramString.equals("")){
            return;
        }
        parseParams(paramString);
    }
    private void parseParams(String paramString){
        //分割  将字符串转成数组
        StringTokenizer token = new StringTokenizer(paramString,"&");
        while (token.hasMoreTokens()){
            String KeyValue = token.nextToken();
            String[] KeyValues = KeyValue.split("=");
            if(KeyValues.length==1){
                KeyValues = Arrays.copyOf(KeyValues,2);
                KeyValues[1]=null;
            }
            String key = KeyValues[0].trim();
            String value = KeyValues[1]==null?null:decode(KeyValues[1].trim(),"utf-8");
            //转换成Map
            if(!parameterMapValues.containsKey(key)){
                parameterMapValues.put(key,new ArrayList<String>());
            }
            List<String> values = parameterMapValues.get(key);
            values.add(value);
        }
    }
    /**
     * 根据页面的name获取对应的值
     */
    public String getParameter(String name){
        String[] values = getParameterValues(name);
        if(values==null){
            return null;
        }
        return values[0];
    }
    public String[] getParameterValues(String name){
        List<String> values = null;
        if((values=parameterMapValues.get(name))==null){
            return null;
        }else {
            return values.toArray(new String[10]);
        }
    }
    //解决中文问题
    private String decode(String value,String code){
        try {
            return java.net.URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }

    public String getUrl() {
        return url;
    }
}
