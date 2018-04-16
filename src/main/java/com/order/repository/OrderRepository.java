package com.order.repository;

import com.order.entity.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * @author 冯志宇 2018/4/16
 * 相当于mybatis的dao包，hibernate数据库操作层
 */
@Repository
public interface OrderRepository extends CrudRepository<Order,Long>{

}
