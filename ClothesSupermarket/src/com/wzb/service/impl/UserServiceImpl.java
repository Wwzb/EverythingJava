package com.wzb.service.impl;

import com.wzb.bean.User;
import com.wzb.service.UserService;
import com.wzb.utils.BusinessException;
import com.wzb.utils.UserIO;

public class UserServiceImpl implements UserService{

    @Override
    public User register(User user) throws BusinessException {
        UserIO userIO = new UserIO();
        userIO.add(user);
        userIO.writeUsers();
        return null;
    }
}

