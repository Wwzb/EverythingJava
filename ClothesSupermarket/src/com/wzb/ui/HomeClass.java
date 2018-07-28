package com.wzb.ui;

import com.wzb.bean.Clothes;
import com.wzb.service.ClothesService;
import com.wzb.service.impl.ClothesServiceImpl;
import com.wzb.utils.ConsoleTable;

import java.util.List;

public class HomeClass extends BaseClass {
    public void show(){
        showProducts();
    }
    public void showProducts(){
        ClothesService clothesService = new ClothesServiceImpl();
        List<Clothes> list = clothesService.list();
        ConsoleTable t = new ConsoleTable(8, true);
        t.appendRow();
        t.appendColum("id")
                .appendColum("brand")
                .appendColum("style")
                .appendColum("color")
                .appendColum("size")
                .appendColum("num")
                .appendColum("price")
                .appendColum("description");
        for(Clothes c:list){
            t.appendRow();
            t.appendColum("id")
                    .appendColum(c.getBrand())
                    .appendColum(c.getStyle())
                    .appendColum(c.getColor())
                    .appendColum(c.getSize())
                    .appendColum(c.getNum())
                    .appendColum(c.getPrice())
                    .appendColum(c.getDescription());
        }
        println(t.toString());
    }
}
