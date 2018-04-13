package com.order.controller;

import com.order.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冯志宇 2018/4/13
 */
@RestController
public class OrderController {
    @Autowired
    OrderDao dao;

    @GetMapping("/name")
    public String getName(){
        return dao.getName();
    }
}
