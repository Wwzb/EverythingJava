package com.wzb.httpserver;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器并启动
 */
public class Server {
    private boolean isShutdown = false;
    private ServerSocket server;
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * 启动方法
     */
    public void start(){
        start(9988);
    }
    //指定端口的启动方法
    public void start(int port){
        try {
            server = new ServerSocket(port);
            this.receive();
        } catch (IOException e) {
            stop();
        }

    }
    /**
     * 接收客户端
     */
    private void receive(){
        try {
            while (!isShutdown){
                new Thread(new Dispatcher(server.accept())).start();
            }
        } catch (IOException e) {
            stop();
        }
    }

    /**
     * 停止服务器
     */
    public void stop(){
        isShutdown = true;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
