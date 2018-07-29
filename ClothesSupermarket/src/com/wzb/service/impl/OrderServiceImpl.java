package com.wzb.service.impl;

import com.wzb.bean.Order;
import com.wzb.service.OrderService;
import com.wzb.utils.BusinessException;
import com.wzb.utils.OrderIO;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderIO orderIO = new OrderIO();
    @Override
    public void buyProduct(Order o) throws BusinessException {
        orderIO.add(o);
    }

    @Override
    public List<Order> list() throws BusinessException {
        return orderIO.list();
    }

    @Override
    public Order findById(int orderId) throws BusinessException {
        return orderIO.findByOrderId(orderId);
    }
}
