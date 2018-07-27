package com.wzb.httpserver;

import java.io.IOException;
import java.net.Socket;

/**
 * 分发器
 * 一个请求与响应 就一个此对象
 */
public class Dispatcher implements Runnable{
    private Socket client;
    private Request req;
    private Response rep;
    private int code = 200;
    public Dispatcher(Socket client){
        this.client = client;
        try {
            req = new Request(client.getInputStream());
            rep = new Response(client.getOutputStream());
        } catch (IOException e) {
            code = 500;
            return;
        }
    }
    @Override
    public void run() {
        Servlet serv = new Servlet();
        serv.service(req,rep);
        try {
            rep.pushToClient(code);//推送到客户端
        } catch (IOException e) {
        try {
            rep.pushToClient(500);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        }
        try {
            client.close();
        } catch (IOException e) {

        }
    }
}
