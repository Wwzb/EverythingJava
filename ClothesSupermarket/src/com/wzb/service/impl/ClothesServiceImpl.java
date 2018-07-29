package com.wzb.service.impl;

import com.wzb.bean.Clothes;
import com.wzb.service.ClothesService;
import com.wzb.utils.BusinessException;
import com.wzb.utils.ClothesIO;


import java.util.List;

public class ClothesServiceImpl implements ClothesService {
    private ClothesIO clothesIO = new ClothesIO();
    @Override
    public List<Clothes> list() throws BusinessException {
        return clothesIO.list();
    }

    @Override
    public Clothes findById(String  cid) throws BusinessException {
        return clothesIO.findById(cid);
    }
    public void update() throws BusinessException{
        clothesIO.update();
    }
}
