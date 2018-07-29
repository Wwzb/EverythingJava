package com.wzb.ui;

import com.wzb.bean.Clothes;
import com.wzb.bean.Order;
import com.wzb.bean.OrderItem;
import com.wzb.service.ClothesService;
import com.wzb.service.OrderService;
import com.wzb.service.impl.ClothesServiceImpl;
import com.wzb.service.impl.OrderServiceImpl;
import com.wzb.utils.BusinessException;
import com.wzb.utils.ConsoleTable;
import com.wzb.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class HomeClass extends BaseClass {
        private ClothesService clothesService = new ClothesServiceImpl();
        private OrderService orderService = new OrderServiceImpl();
    public void show(){
        showProducts();
        println("welcome:"+currUser.getUsername());
        menu();
    }
    private void menu(){
        boolean flag = true;
        while (flag){
            println(getString("home.function"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select){
                case "1"://1、查询全部订单
                    findOrderList();
                    flag=false;
                    break;
                case "2"://2、查找订单
                    findOrderById();
                    flag=false;
                    break;
                case "3"://3、购买
                    try {
                        buyProducts();
                        flag=false;
                    }catch (BusinessException e){
                        println(e.getMessage());
                    }
                    break;

                case "0"://0、退出
                    flag=false;
                    System.exit(0);
                    break;
                default:
                    println(getString("input.error"));
                    break;
            }
        }
    }
    private void buyProducts() throws BusinessException {
        //生成订单
        boolean flag = true;
        int count = 1;
        float sum = 0.0f;//订单总金额
        Order order = new Order();
        while (flag){
            println(getString("product.input.id"));
            String id = input.nextLine();
            println(getString("product.input.shoppingNum"));
            String shoppingNum =input.nextLine();
            OrderItem orderItem = new OrderItem();
            Clothes clothes = clothesService.findById(id);
            int num = Integer.parseInt(shoppingNum);
            if(num>clothes.getNum()){
                throw new BusinessException("product.num.error");
            }
            //一条订单明细
            clothes.setNum(clothes.getNum()-num);//减去库存
            orderItem.setClothes(clothes);
            orderItem.setShoppingNum(num);
            orderItem.setSum(clothes.getPrice()*num);
            sum = sum + orderItem.getSum();
            orderItem.setItemId(count++);
            order.getOrderItemList().add(orderItem);
            println(getString("product.buy.continue"));
            String buy = input.nextLine();
            switch (buy){
                case "1":
                    flag=true;
                    break;
                case "2":
                    flag=false;
                break;
                default:
                    flag=false;
                    break;
            }
        }
        order.setCreateDate(DateUtils.toDate(new Date()));
        order.setUserId(currUser.getId());
        order.setSum(sum);
        order.setOrderId(orderService.list().size()+1);
        orderService.buyProduct(order);
        clothesService.update();
        show();
    }

    private void findOrderById() {
        println(getString("product.order.input.oid"));
        String oid = input.nextLine();
        Order order = orderService.findById(Integer.parseInt(oid));
        if(order!=null){
            showOrder(order);
        }else {
            println(getString("product.order.error"));
        }
        menu();
    }

    private void findOrderList() {
        List<Order> list = orderService.list();
        for(Order o:list){
            showOrder(o);
        }
        menu();
    }
    private void showOrder(Order o){
        print(getString("product.order.oid")+o.getOrderId());
        print("\t"+getString("product.order.createDate")+o.getCreateDate());
        println("\t"+getString("product.order.sum")+o.getSum());
        ConsoleTable t = new ConsoleTable(9, true);
        t.appendRow();
        t.appendColum("itemId")
                .appendColum("brand")
                .appendColum("style")
                .appendColum("color")
                .appendColum("size")
                .appendColum("price")
                .appendColum("description")
                .appendColum("shoppingNum")
                .appendColum("sum");
        for(OrderItem item :o.getOrderItemList()) {
            t.appendRow();
            t.appendColum(item.getItemId())
                    .appendColum(item.getClothes().getBrand())
                    .appendColum(item.getClothes().getStyle())
                    .appendColum(item.getClothes().getColor())
                    .appendColum(item.getClothes().getSize())
                    .appendColum(item.getClothes().getPrice())
                    .appendColum(item.getClothes().getDescription())
                    .appendColum(item.getSum())
                    .appendColum(item.getSum());
        }
        println(t.toString());
    }
    public void showProducts(){
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
            t.appendColum(c.getId())
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