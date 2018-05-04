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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        String url="https:" + domain + "/wxpay/xcxPay";
        String msg = OkHttpUtil.postRequest(url, JsonUtils.toJson(save));
        JSONObject object = JSONObject.parseObject(msg);
        Object data = object.get("data");
        return data.toString();
    }

    /**
     * 我的全部订单,根据openid查询我的订单
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param sessionid 会话标识
     * */
    @GetMapping(value = "/getAllOrderByOpenid/{pageNum}/{pageSize}/{sessionid}")
    public Page<Order> getAllOrder(@PathVariable("pageNum")int pageNum,
                                   @PathVariable("pageSize")int pageSize,
                                   @PathVariable("sessionid") String sessionid){
        //根据sessionid取出当前用户的会话，并获取openid
        HttpSession session = listener.getSession(sessionid);
        if (session==null){
            System.out.println("重新登录");
            return null;
        }
        String openid = (String) session.getAttribute("openid");
        return orderService.getAllOrder(openid,pageNum,pageSize);
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
