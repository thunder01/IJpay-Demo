package com.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 冯志宇 2018/4/13
 * 订单实体
 */
@Entity
@Table(name = "table_order")
public class Order implements Serializable{
    @Id
    @GeneratedValue
    private long       id;
    @Column
    private long       chargeId;
    @Column
    private String     machineNo;
    @Column
    private String     openid;
    @Column
    private String     orderNo;
    @Column
    private int        orderStatus;
    @Column
    private int        payChannel;
    @Column
    private String     outTradeNo;
    @Column
    private BigDecimal energyNum;
    @Column
    private BigDecimal energySum;
    @Column
    private BigDecimal orderSum;
    @Column
    private Timestamp  createtime;
    @Column
    private Timestamp  paytime;
    @Column
    private int        openBill;
    @Column
    private String     billNum;

    private String sessionid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChargeId() {
        return chargeId;
    }

    public void setChargeId(long chargeId) {
        this.chargeId = chargeId;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getEnergyNum() {
        return energyNum;
    }

    public void setEnergyNum(BigDecimal energyNum) {
        this.energyNum = energyNum;
    }

    public BigDecimal getEnergySum() {
        return energySum;
    }

    public void setEnergySum(BigDecimal energySum) {
        this.energySum = energySum;
    }

    public BigDecimal getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(BigDecimal orderSum) {
        this.orderSum = orderSum;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getPaytime() {
        return paytime;
    }

    public void setPaytime(Timestamp paytime) {
        this.paytime = paytime;
    }

    public int getOpenBill() {
        return openBill;
    }

    public void setOpenBill(int openBill) {
        this.openBill = openBill;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", chargeId=" + chargeId +
                ", machineNo='" + machineNo + '\'' +
                ", openid='" + openid + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus=" + orderStatus +
                ", payChannel=" + payChannel +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", energyNum=" + energyNum +
                ", energySum=" + energySum +
                ", orderSum=" + orderSum +
                ", createtime=" + createtime +
                ", paytime=" + paytime +
                ", openBill=" + openBill +
                ", billNum='" + billNum + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }
}
