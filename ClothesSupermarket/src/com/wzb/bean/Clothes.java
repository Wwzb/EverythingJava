package com.wzb.bean;

import java.io.Serializable;

public class Clothes implements Serializable {
    private String id;//商品编号
    private String brand;//品牌
    private String style;//款式
    private String color;//颜色
    private String size;//尺码
    private String description;//描述
    private int num;//库存数量
    private float price;//单价

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Clothes(String id, String brand, String style, String color, String size, String description, int num, float price) {
        this.id = id;
        this.brand = brand;
        this.style = style;
        this.color = color;
        this.size = size;
        this.description = description;
        this.num = num;
        this.price = price;
    }
}
