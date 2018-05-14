package com.order.service.serviceimpl;

import com.opslab.util.RandomUtil;
import com.order.entity.Order;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author 冯志宇 2018/4/13
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        /**生成订单编号*/
        order.setOrderNo(RandomUtil.squid());
        Order save = orderRepository.save(order);
        return save;
    }

    @Override
    public boolean updateOrderStatus(long id,int status) {
        Order one = orderRepository.findOne(id);
        one.setOrderStatus(status);
        if (status==2){
            one.setPaytime(new Timestamp(System.currentTimeMillis()));
        }
        Order save = orderRepository.save(one);
        if (save==null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Order getById(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Page<Order> getAllOrder(String opneid,int pageNum,int pageSize) {
        /**设置排序方式，按照创建日期倒序*/
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"createtime"));
        /**设置分页信息,页数是从0开始的*/
        Pageable pageable=new PageRequest(pageNum-1,pageSize,sort);
        Page<Order> all = orderRepository.findByOpenid(opneid,pageable);
        return all;
    }

    @Override
    public Page<Order> getByOrderStatus(int pageNum,int pageSize,String openid,int status) {
        /**设置排序方式，按照创建日期倒序*/
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"createtime"));
        /**设置分页信息,页数是从0开始的*/
        Pageable pageable=new PageRequest(pageNum-1,pageSize,sort);
        Page<Order> result=orderRepository.findByOpenidAndOrderStatus(openid,status,pageable);
        return result;
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }

    @Override
    public Order updateOrderOutTrandeNo(Order order) {
        Order one = orderRepository.findOne(order.getId());
        one.setOutTradeNo(order.getOrderNo());
        Order save = orderRepository.save(one);

        return save;
    }
}
