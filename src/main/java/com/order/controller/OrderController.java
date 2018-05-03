package com.order.controller;

import com.alibaba.fastjson.JSON;
import com.order.entity.Order;
import com.order.service.OrderService;
import com.wechat.utils.JsonUtils;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        boolean flag = orderService.updateOrderStatus(8,2);
        return flag;
    }

    /**
     * 生成订单
     * */
    @PostMapping(value = "/saveOrder")
    public void saveOrder(@RequestBody Order order, HttpServletRequest httpServletRequest, HttpServletResponse response){
        /**
         * 先生成订单
         * */
        String openid = (String) httpServletRequest.getSession().getAttribute("openid");
        order.setOpenid(openid);
        Order save = orderService.save(order);
        /**
         * 然后请求支付接口
         * */
        //创建OkHttpClient对象
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        okhttp3.RequestBody requestBody= okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtils.toJson(save));
        final Request request = new Request.Builder()
                .url("https://m.vipvipgo.cn/wxpay/webPay")//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        try {
            Response response1 = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * 根据状态查询订单
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
