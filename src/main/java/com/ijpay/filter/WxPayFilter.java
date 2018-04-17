package com.ijpay.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 冯志宇 2018/3/23
 * 此过滤器的目的是解决跨域问题，在controller添加的@Crossorigin不好使了
 * 在所有的响应信息头中添加允许跨域的设置
 * 这会引起安全问题
 */
@WebFilter(urlPatterns = "/*",filterName = "wxPayFilter")
public class WxPayFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials","true");

        filterChain.doFilter(servletRequest,response);
    }

    @Override
    public void destroy() {

    }
}
