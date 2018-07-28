package com.wzb.ui;

import com.wzb.bean.User;
import com.wzb.service.UserService;
import com.wzb.service.impl.UserServiceImpl;
import com.wzb.utils.BusinessException;

public class LoginClass extends BaseClass{

    public void login() throws BusinessException {
        println(getString("input.username"));
        String username = input.nextLine();
        println(getString("input.password"));
        String password = input.nextLine();
        User user = new User(username,password);
        UserService userService = new UserServiceImpl();
        userService.login(username,password);
        if(user!=null){
            currUser = user;
        }else {
            throw new BusinessException("login.error");
        }
    }

}
