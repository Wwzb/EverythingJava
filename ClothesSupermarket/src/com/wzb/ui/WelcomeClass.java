package com.wzb.ui;

public class WelcomeClass extends BaseClass{
    public void start(){
        println(getString("info.welcome"));
        boolean flag = true;
        while (flag){
            println(getString("info.login.reg"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select){
                case "1":
                    flag = false;
                    System.out.println("登录");
                    break;
                case "2":
                    flag = false;
                    System.out.println("注册");
                    break;
                default:
                    println(getString("input.error"));
                    break;
            }
        }
    }
}
