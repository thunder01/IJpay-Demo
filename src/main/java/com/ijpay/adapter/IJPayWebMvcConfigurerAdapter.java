package com.ijpay.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.ijpay.interceptor.AliPayInterceptor;
import com.ijpay.interceptor.WxPayInterceptor;

/**
 * 添加拦截器
 * addPathPatterns 用于添加拦截规则
 * excludePathPatterns 用户排除拦截
 * */
@Configuration
public class IJPayWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AliPayInterceptor()).addPathPatterns("/alipay/**");
        registry.addInterceptor(new WxPayInterceptor()).addPathPatterns("/wxpay/**","/wxsubpay/**");
        super.addInterceptors(registry);
    }
}

