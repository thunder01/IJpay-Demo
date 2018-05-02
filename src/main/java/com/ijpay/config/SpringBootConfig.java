package com.ijpay.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import com.jfinal.template.ext.spring.JFinalViewResolver;

/**
 * web页面的相关配置
 * */
@Configuration
public class SpringBootConfig {
	/**
	 * 使用JFinal配置的视图
	 * */
	@Bean(value = "jfinalViewResolver")
	public JFinalViewResolver getJFinalViewResolver(){
		JFinalViewResolver jf = new JFinalViewResolver();
		jf.setDevMode(true);
		jf.setCache(false);
		jf.setPrefix("/WEB-INF/_views/");
		jf.setContentType("text/html;charset=UTF-8");
		jf.setOrder(0);
		return jf;
	}
}
