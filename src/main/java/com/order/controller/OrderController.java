package com.order.controller;

import com.order.entity.Order;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

/**
 * @author 冯志宇 2018/4/13
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public boolean test(){
        Order order=new Order();
        order.setMachineNo("123345678");
        order.setOpenid("1240000000");
        order.setChargeId(100);
        boolean flag = orderService.cancelOrder(8);
        return flag;
    }

    /**
     * 生成订单
     * */
    @PostMapping(value = "/saveOrder")
    public boolean saveOrder(@RequestBody Order order){
        return orderService.save(order);
    }

    /**
     * 全部订单
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * */
    @GetMapping(value = "/getAllOrder/{pageNum}/{pageSize}")
    public Page<Order> getAllOrder(@PathVariable("pageNum")int pageNum,
                                   @PathVariable("pageSize")int pageSize){
        return orderService.getAllOrder(pageNum,pageSize);
    }

    @GetMapping(value = "/getByStatus/{pageNum}/{pageSize}/{status}")
    public Page<Order> getByStatus(@PathVariable("pageNum")int pageNum,
                                   @PathVariable("pageSize")int pageSize,
                                   @PathVariable("status") int status){
        return orderService.getByOrderStatus(pageNum,pageSize,status);
    }
}
