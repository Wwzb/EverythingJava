package com.wzb.tcp.chat.demo01;

/**
 * 聊天室服务器端
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private List<MyChannel> all = new ArrayList<MyChannel>();//创建用户容器
    public static void main(String[] args) throws IOException {
        new Server().connect();//线程开始
    }
    public void connect() throws IOException {
        ServerSocket server = new ServerSocket(9988);//创建服务器TCP
        while (true) {
            Socket Client = server.accept();//用户连接
            MyChannel channel = new MyChannel(Client);//创建新用户
            all.add(channel);//将用户加入容器
            new Thread(channel).start();
        }
    }
    class MyChannel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning = true;
        private String name;
        public MyChannel(Socket client) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                this.name = dis.readUTF();
                this.send("欢迎进入聊天室");
                sendOthers(this.name+"进入了聊天室",true);
            } catch (IOException e) {
                CloseUtil.closeAll(dis, dos);//发送异常，关闭io，移除该对象
                isRunning = false;
                all.remove(this);
            }
        }
        private String receive() {
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                CloseUtil.closeAll(dis);
                isRunning = false;
                all.remove(this);
            }
            return msg;
        }
        private void send(String msg) {
            Date date = new Date();//系统时间戳
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            if (msg == null || msg.equals("")) {
                return;
            }
            try {
                dos.writeUTF(time + "-->" + msg);
                dos.flush();
            } catch (IOException e) {
                CloseUtil.closeAll(dos);
                isRunning = false;
                all.remove(this);
            }
        }
        private void sendOthers(String msg,boolean sys) {
            if (msg.startsWith("@")&&msg.indexOf(":")>-1) {//获取用户名
                String name = msg.substring(1,msg.indexOf(":"));
                String content = msg.substring(msg.indexOf(":")+1);
                for (MyChannel other : all) {//私聊功能
                    if(other.name.equals(name)){
                        other.send(this.name+"对您悄悄的说:"+content);
                    }
                }
            } else {//群聊
                for (MyChannel other : all) {
                    if (other == this) {
                        continue;
                    }
                    if (sys) {
                        other.send("系统信息:"+msg);
                    }else {
                        other.send(this.name + "对所有人说：" + msg);
                    }
                }
            }
        }
        @Override
        public void run() {
            while (isRunning) {
                sendOthers(receive(),false);
            }
        }
    }
}