package com.wzb.service;

import com.wzb.bean.User;
import com.wzb.utils.BusinessException;

public interface UserService {

    public User register(User user) throws BusinessException;
    public User login(String username,String password)throws BusinessException;
}
