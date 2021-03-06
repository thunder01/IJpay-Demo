package com.ijpay.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MySessionListener;
import com.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.ijpay.entity.WxPayBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wechat.utils.JsonUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MySessionListener listener;

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WxPayBean wxPayBean;

    @RequestMapping("")
    @ResponseBody
    public String index(){
    	logger.info("欢迎使用IJPay 开发加群148540125交流 -By Javen");
    	return "欢迎使用IJPay 开发加群148540125交流 -By Javen";
    }

    @RequestMapping("/toOauth")
    public void toOauth(HttpServletResponse response,@RequestParam("state")String state){
    	try {
            //访问这个地址到授权页面，授权后获取code
        	String url = wxService.oauth2buildAuthorizationUrl("https://"+wxPayBean.getDomain()+"/oauth", WxConsts.OAUTH2_SCOPE_USER_INFO, state);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 网页授权登陆
     * @param request
     * @param response
     * @param code
     */
    @RequestMapping(value = "/oauth",method = RequestMethod.GET)
    public void oauth(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam(value = "code",required = true)String code){
        /**
         * 微信内置的浏览器特殊情况,响应的set-cookie不起作用，所以无法存储JSSESSIONID
         * 所以手动设置
         * */
        Cookie cookie=new Cookie("sessionid",request.getSession().getId());
        response.addCookie(cookie);

    	try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			WxMpUser wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			logger.info("授权获取到的用户信息："+JsonUtils.toJson(wxMpUser));
			String openId = wxMpUser.getOpenId();
            request.getSession().setAttribute("openId", openId);
            response.sendRedirect("https://"+wxPayBean.getDomain()+"/towxpay");
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 跳转到微信H5支付demo页面
     * */
    @RequestMapping("/toWxH5Pay")
    public String toWxH5Pay(){
		return "wxh5pay.html";
	}

	/**
     * 跳转到微信公众号及扫码支付demo
     * */
    @RequestMapping("/towxpay")
    public String towxpay() {
		return "wxpay.html";
	}

	/**
     * 微信服务商demo
     * */
    @RequestMapping("/towxsubpay")
	public String towxsubpay() {
		return "wxsubpay.html";
	}

    @RequestMapping(value = "/pay_input_money")
    public ModelAndView pay_input_money(){
    	 ModelAndView mav = new ModelAndView("pay_input_money.html");
         mav.addObject("content", "xxx");
         return mav;
    }
    
    @RequestMapping(value = "/pay_keyboard")
    public String pay_keyboard(){
    	return "pay_keyboard.html";
    }
    
    @RequestMapping(value = "/pay_select_money")
    public String pay_select_money(){
    	return "pay_select_money.html";
    }
    

    @RequestMapping("/success")
	public String success() {
		return "success.html";
	}
    
    
    @RequestMapping(value = "/ss/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String pa(@PathVariable("id") Integer id){
        return  "id>" + id;
    }

    //获取参数
    @RequestMapping(value = "/xx",method = RequestMethod.GET)
    @ResponseBody
    public String param(@RequestParam("id") Integer xx){
        return  "id>"+xx;
    }

    //设置默认值
    @RequestMapping(value = "/xxx",method = RequestMethod.GET)
    @ResponseBody
    public String param2(@RequestParam(value = "id",required = false ,defaultValue = "2") Integer xx){
        return  "id>"+xx;
    }

    /**
     * 医院结算项目
     */
    @GetMapping(value = "/hospital")
    public String hospital(){
        return "hospital/01_index.html";
    }

}
