package com.wzb.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.wzb.bean.Clothes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsXmlUtils {
    public static List<Clothes> parserProductFromXml(){
        List<Clothes> products = new ArrayList<>();
        XStream xStream = new XStream(new Xpp3Driver());
        xStream.alias("list",products.getClass());
        xStream.alias("clothes",Clothes.class);
        xStream.useAttributeFor(Clothes.class,"id");
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("products.xml"));
            products = (List<Clothes>) xStream.fromXML(inputStream);
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return products;
    }
    public static void writeProductToXml(List<Clothes> products){
        XStream xStream = new XStream(new Xpp3Driver());
        xStream.alias("list",products.getClass());
        xStream.alias("Clothes",Clothes.class);
        xStream.useAttributeFor(Clothes.class,"id");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("products.xml"));
            outputStream.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>".getBytes());
            xStream.toXML(products,outputStream);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
