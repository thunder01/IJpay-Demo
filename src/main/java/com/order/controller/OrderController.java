package com.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.util.MySessionListener;
import com.util.OkHttpUtil;
import com.order.entity.Order;
import com.order.service.OrderService;
import com.wechat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 冯志宇 2018/4/13
 */
@RestController
public class OrderController {
    @Value("${wxpay.domain}")
    private String domain;

    @Autowired
    private MySessionListener listener;

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
    public String saveOrder(HttpServletResponse response, @RequestBody Order order){
        /**
         * 先生成订单
         * */
        System.out.println("sessionid  "+order.getSessionid());
        HttpSession session = listener.getSession(order.getSessionid());
        if (session==null){
            return "请重新登录";
        }

        System.out.println("openid  "+session.getAttribute("openid"));
        String openid = (String) session.getAttribute("openid");
        if (openid==null||"".equals(openid)){
            return "error:opneid can not be null";
        }
        order.setOpenid(openid);
        Order save = orderService.save(order);
        /**
         * 然后请求支付接口
         * */
        //String url="https:" + domain + "/wxpay/xcxPay";
        String url="http://192.168.100.120:8080/wxpay/xcxPay";
        String msg = OkHttpUtil.postRequest(url, JsonUtils.toJson(save));
        JSONObject object = JSONObject.parseObject(msg);
        Object data = object.get("data");
        return data.toString();
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
