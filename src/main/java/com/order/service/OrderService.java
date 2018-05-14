package com.order.service;

import com.order.entity.Order;
import org.springframework.data.domain.Page;

/**
 * @author 冯志宇 2018/4/13
 */
public interface OrderService {
    /**
     * 生成订单
     * @param order
     * @return
     */
    Order save(Order order);

    /**
     * 修改订单状态
     * @param id
     * @return
     */
    boolean updateOrderStatus(long id,int status);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order getById(long id);

    /**
     * 获取我的订单
     * @param openid 用户的唯一标识
     * @param pageNum 当前页数
     * @param pageSize 每页显示条数
     * @return
     */
    Page<Order> getAllOrder(String openid,int pageNum,int pageSize);

    /**
     * 根据订单状态查询
     * @param pageNum 当前页数
     * @param pageSize 每页显示条数
     * @param status 0：未付款；1：已取消；2：已付款；3：退款申请；4：已完成
     * @return
     */
    Page<Order> getByOrderStatus(int pageNum,int pageSize,String opneid,int status);

    /**
     * 根据订单号查询订单信息
     * @param orderNo 订单编号
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 修改订单,补充out_trade_no信息以及支付时间信息
     * @param order
     */
    Order updateOrderOutTrandeNo(Order order);
}
