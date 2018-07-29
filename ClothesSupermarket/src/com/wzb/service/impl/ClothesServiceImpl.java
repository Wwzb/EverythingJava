package com.wzb.service.impl;

import com.wzb.bean.Clothes;
import com.wzb.service.ClothesService;
import com.wzb.utils.BusinessException;
import com.wzb.utils.ProductsXmlUtils;

import java.util.List;

public class ClothesServiceImpl implements ClothesService {

    @Override
    public List<Clothes> list() throws BusinessException {
        List<Clothes> clothes = ProductsXmlUtils.parserProductFromXml();
        return clothes;
    }

    @Override
    public Clothes findById(int cid) throws BusinessException {
        return null;
    }
}
