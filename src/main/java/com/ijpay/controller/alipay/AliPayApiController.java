package com.ijpay.controller.alipay;

import com.jpay.alipay.AliPayApiConfig;

public abstract class AliPayApiController{
	/**
	 * 获取支付宝支付的配置信息
	 * */
	public abstract  AliPayApiConfig getApiConfig();
}