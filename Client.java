package com.wzb.tcp.chat.demo01;

/**
 * 聊天室客户端
 */

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    public static void main(String[] args) throws IOException {
        System.out.println("请输入昵称:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//从控制台输入昵称
        String name = br.readLine();
        if(name.equals("")){
            return;
        }
        Socket client = new Socket("localhost",9988);//连接服务器，端口为9988
        new Thread(new Send(client,name)).start();//发送消息线程
        new Thread(new Receive(client)).start();//接受消息线程
    }
}
class Send extends Thread{//发送类
    private BufferedReader console;
    private DataOutputStream dos;
    private boolean isRunning = true;//线程运行标志位
    private String name;//昵称
    public Send(){
        console = new BufferedReader(new InputStreamReader(System.in));//在默认构造方法中初始化控制台输入对象
    }
    public Send(Socket client,String name){//发送方法
        this();
        try {
            dos = new DataOutputStream(client.getOutputStream());
            this.name = name;
            send(this.name);
        } catch (IOException e) {
           isRunning = false;//如果发生异常，则中止线程，关闭io
            CloseUtil.closeAll(dos,console);
        }
    }
    private String getMsg(){//获取控制台输入的信息
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void send(String msg){
        if(msg!=null&&!msg.equals("")){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                CloseUtil.closeAll(dos,console);
            }
        }
    }
    @Override
    public void run() {
        while (isRunning){//重写run方法，若标志位为真则无限循环
            send(getMsg());
        }
    }
}
class Receive extends Thread{//接受方法
    private DataInputStream dis ;
    private boolean isRunning = true;
    public Receive(){

    }
    public Receive(Socket client){
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
           isRunning = false;
           CloseUtil.closeAll(dis);
        }
    }
    public String getMsg(){
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
        return msg;
    }
    @Override
    public void run() {
        while (isRunning){
            System.out.println(getMsg());
        }
    }
}