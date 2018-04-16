package com.order.controller;

import com.order.dao.OrderDao;
import com.order.entity.Order;
import com.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冯志宇 2018/4/13
 */
@RestController
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @GetMapping("/save")
    public Order saveOrder(){
        Order order=new Order();
        order.setMachineNo("123345678");
        order.setOpenid("1240000000");
        order.setChargeId(100);

        Order save = repository.save(order);
        return save;
    }
}
