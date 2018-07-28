package com.wzb.ui;

import com.wzb.utils.BusinessException;
import com.wzb.utils.UserIO;

public class WelcomeClass extends BaseClass{
    public void start(){
        println(getString("info.welcome"));
        UserIO userIO = new UserIO();
        userIO.readUsers();
        boolean flag = true;
        while (flag){
            println(getString("info.login.reg"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select){
                case "1":
                    try {
                        new LoginClass().login();
                        flag = false;
                        println(getString("login.success"));
                    }catch (BusinessException e){
                println(getString(e.getMessage()));
                    }
                    break;
                case "2":
                    try {
                        new RegisterClass().register();
                        println(getString("reg.success"));
                        flag = false;
                    }catch (BusinessException e){
                        println(getString("reg.error"));
                    }
                    break;
                default:
                    println(getString("input.error"));
                    break;
            }
        }
        HomeClass homeClass = new HomeClass();
        homeClass.show();
    }
}
