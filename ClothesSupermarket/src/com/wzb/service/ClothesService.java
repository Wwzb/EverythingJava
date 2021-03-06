package com.wzb.service;

import com.wzb.bean.Clothes;
import com.wzb.utils.BusinessException;

import java.util.List;

public interface ClothesService {
    public List<Clothes> list() throws BusinessException;
    public Clothes findById(String cid)throws BusinessException;
    public void update() throws BusinessException;
}
