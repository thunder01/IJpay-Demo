package com.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.util.OkHttpUtil;
import com.order.entity.Order;
import com.order.service.OrderService;
import com.wechat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 冯志宇 2018/4/13
 */
@RestController
public class OrderController {
    @Value("${wxpay.domain}")
    private String domain;

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * */
    @PostMapping(value = "/saveOrder")
    public String saveOrder(@RequestBody Order order){
        /**
         * 先生成订单
         * */
        Order save = orderService.save(order);
        /**
         * 然后请求支付接口
         * */
        String url="https:" + domain + "/wxpay/xcxPay";
        String msg = OkHttpUtil.postRequest(url, JsonUtils.toJson(save));
        JSONObject object = JSONObject.parseObject(msg);
        Object data = object.get("data");
        if(data!=null){
            return data.toString();
        }else {
            return null;
        }
    }

    /**
     * 我的全部订单,根据openid查询我的订单
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param openid 用户的唯一标识
     * */
    @GetMapping(value = "/getAllOrderByOpenid/{pageNum}/{pageSize}/{openid}")
    public Page<Order> getAllOrder(@PathVariable("pageNum")int pageNum,
                                   @PathVariable("pageSize")int pageSize,
                                   @PathVariable("openid") String openid){
        return orderService.getAllOrder(openid, pageNum, pageSize);
    }

    /**
     * 根据状态查询订单，查询已取消订单或已完成订单
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param status 订单状态值
     * @return
     */
    @GetMapping(value = "/getByStatus/{pageNum}/{pageSize}/{status}")
    public Page<Order> getByStatus(@PathVariable("pageNum")int pageNum,
                                   @PathVariable("pageSize")int pageSize,
                                   @PathVariable("status") int status){
        return orderService.getByOrderStatus(pageNum,pageSize,status);
    }

    /**
     * 根据id查询订单
     * @param id 订单id
     * @return
     */
    @GetMapping(value = "/getOrderById/{id}")
    public Order getOrderById(@PathVariable("id") long id){
        return orderService.getById(id);
    }

    /**
     * 修改订单状态，如取消订单，或订单完成都调用此接口
     * @param order
     * @return
     */
    @PostMapping(value = "/editStatus")
    public boolean editStatus(@RequestBody Order order){
        return orderService.updateOrderStatus(order.getId(),order.getOrderStatus());
    }
}
