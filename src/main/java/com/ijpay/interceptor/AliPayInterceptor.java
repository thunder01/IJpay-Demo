package com.ijpay.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.ijpay.controller.alipay.AliPayApiController;
import com.jpay.alipay.AliPayApiConfigKit;

/**
 * 拦截所有支付宝支付请求
 * 目的是请求处理前加载配置信息
 * */
public class AliPayInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object handler) throws Exception {
		if (HandlerMethod.class.equals(handler.getClass())) {
			HandlerMethod method = (HandlerMethod) handler;
			Object controller = method.getBean();
			if (controller instanceof AliPayApiController == false) {
				throw new RuntimeException("控制器需要继承 AliPayApiController");
			}
			
			try {
				/**
				 * 加载支付宝支付的配置
				 * */
				AliPayApiConfigKit.setThreadLocalAliPayApiConfig(((AliPayApiController)controller).getApiConfig());
				return true;
			}
			finally {
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}
