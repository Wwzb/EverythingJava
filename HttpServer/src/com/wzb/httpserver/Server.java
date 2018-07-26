package com.wzb.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器并启动
 */
public class Server {
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
            byte[] data = new byte[20480];
            int len = client.getInputStream().read(data);
            //接收客户端的请求信息
            String requestInfo = new String(data,0,len).trim();
            System.out.println(requestInfo);

            Response rep = new Response(client.getOutputStream());
            rep.println("<html><head><title>这是一个响应</title>");
            rep.println("</head><body>Hello WZB!</body></html>");
            rep.pushToClient(200);
        } catch (IOException e) {
        }
    }

    /**
     * 停止服务器
     */
    public void stop(){

    }

}
