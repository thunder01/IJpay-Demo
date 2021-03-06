package com.ijpay.controller.wxpay;

import com.jpay.weixin.api.WxPayApiConfig;

import javax.servlet.http.HttpServletRequest;

public abstract class WxPayApiController{
	/**
	 * 获取微信支付的配置信息
	 * */
	public abstract WxPayApiConfig getApiConfig(HttpServletRequest httpServletRequest);
}