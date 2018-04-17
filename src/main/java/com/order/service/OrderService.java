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
    boolean save(Order order);

    /**
     * 取消订单
     * @param id
     * @return
     */
    boolean cancelOrder(long id);

    /**
     * 订单状态置为已完成
     * @return
     */
    boolean finishOrder(long id);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order getById(long id);

    /**
     * 获取全部订单
     * @param pageNum 当前页数
     * @param pageSize 每页显示条数
     * @return
     */
    Page<Order> getAllOrder(int pageNum,int pageSize);

    /**
     * 根据订单状态查询
     * @param pageNum 当前页数
     * @param pageSize 每页显示条数
     * @param status 0：未付款；1：已取消；2：已付款；3：退款申请；4：已完成
     * @return
     */
    Page<Order> getByOrderStatus(int pageNum,int pageSize,int status);
}
