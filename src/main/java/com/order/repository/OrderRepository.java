package com.order.repository;

import com.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 冯志宇 2018/4/16
 * 相当于mybatis的dao包，hibernate数据库操作层
 */
@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
    /**
     * 按订单状态查找
     * */
    Page<Order> findByOrderStatus(int status, Pageable page);

    /**
     * 查询我的订单
     */
    Page<Order> findByOpenid(String openid,Pageable page);
}
