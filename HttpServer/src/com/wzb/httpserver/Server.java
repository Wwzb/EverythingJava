package com.wzb.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 创建服务器并启动
 */
public class Server {
    public static final String CRLF="\r\n";
    public static final String BLANK=" ";
    private ServerSocket server;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * 启动方法
     */
    public void start(){
        try {
            server = new ServerSocket(9988);
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接收客户端
     */
    private void receive(){
        try {
            Socket client = server.accept();
            StringBuilder sb = new StringBuilder();
            String msg = null;
            byte[] data = new byte[20480];
            int len = client.getInputStream().read(data);
            //接收客户端的请求信息
            String requestInfo = new String(data,0,len).trim();
            System.out.println(requestInfo);

            //响应
            StringBuilder responseContext = new StringBuilder();
            responseContext.append("<html><head><title>这是一个响应</title>"+"</head><body>Hello WZB!</body></html>");
            StringBuilder response = new StringBuilder();
            //1)HTTP 协议版本、状态代码、描述
            response.append("HTTP/1.1").append(BLANK).append("200").append(BLANK).append("OK").append(CRLF);
            //2)响应头(response head)
            response.append("Server:WZB Server/0.0.1").append(CRLF);
            response.append("Date:").append(new Date()).append(CRLF);
            response.append("Content-type:text/html;charset=utf-8").append(CRLF);
            //正文长度
            response.append("Content-Length:").append(responseContext.toString().getBytes().length).append(CRLF);
            //3)正文
            response.append(CRLF);
            response.append(responseContext);

            //输出流
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(response.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止服务器
     */
    public void stop(){

    }

}
