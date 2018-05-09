package com.ijpay.controller;

import com.alibaba.fastjson.JSON;
import com.util.OkHttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 小程序登录
 * @author 冯志宇 2018/5/9
 */
@RestController
public class LoginController {
    @Value("${xcx.appid}")
    private String xcx_appid;
    @Value("${xcx.secrect}")
    private String xcx_secrect;

    /**
     * 微信小程序登陆
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @RequestMapping(value = "/xcxOauth",method = RequestMethod.POST)
    public void xcxOauth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         @RequestBody Map<String, String> map){
        /**
         * 从请求体重提取code值
         */
        String code="";
        if (map.containsKey("code")){
            code=map.get("code");
        }

        /**
         * 使用OkHttp3发起微信小程序认证请求
         */
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+xcx_appid+"&secret="+xcx_secrect+"&js_code="+code+"&grant_type=authorization_code";
        String respMsg = OkHttpUtil.getRequest(url);
        System.out.println("响应信息"+respMsg);

        Map maps = (Map) JSON.parse(respMsg);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("openid",(String)maps.get("openid"));
        session.setAttribute("session_key",(String)maps.get("session_key"));

        /**
         * 把sessionid放在响应头中，返回给前端的，之后前端的每次请求都要带上sessionid
         * 以维护会话状态，因为小程序跟浏览器不一样，好像没有cookie
         * */
        httpServletResponse.setHeader("openid",(String)maps.get("openid"));
        httpServletResponse.setHeader("sessionid",session.getId());
    }

    /**
     * 退出登录状态
     * @param request
     */
    @GetMapping("/exit")
    public void exit(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
