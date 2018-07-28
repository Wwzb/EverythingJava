package com.wzb.test;

import com.wzb.bean.Clothes;
import com.wzb.utils.ProductsXmlUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ProductXmlUtilsTest {
    @Test
    public void test(){
        List<Clothes> clothes = ProductsXmlUtils.parserProductFromXml();
        System.out.println(Arrays.toString(clothes.toArray()));
    }
}
