package com.order.test;

import com.alibaba.fastjson.JSONObject;
import com.order.SpringRestDocApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Junit单元测试并且生成文档
 * @author 冯志宇 2018/5/11
 */
public class OrderControllerTest extends SpringRestDocApplicationTests {

    /**
     * 测试根据订单状态分页查询的api
     * @throws Exception
     */
    @Test
    public void getByStatusTest() throws Exception{
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/getByStatus/1/10/2").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(document("restful-order-profile"
                        /*responseFields(
                                subsectionWithPath("data.content.id").description("主键标识").type("long"),
                                subsectionWithPath("data.content.chargeId").description("充电信息主键").type("long"),
                                subsectionWithPath("data.content.machineNo").description("充电桩编号").type("String"),
                                subsectionWithPath("data.content.openid").description("用户唯一标识").type("String"),
                                subsectionWithPath("data.content.orderNo").description("订单编号").type("String"),
                                subsectionWithPath("data.content.orderStatus").description("订单状态,0未支付,1已取消,2已支付").type("int"),
                                subsectionWithPath("data.content.payChannel").description("支付渠道").type("0微信,1支付宝"),
                                subsectionWithPath("data.content.outTradeNo").description("第三方支付订单号").type("String"),
                                subsectionWithPath("data.content.energyNum").description("购买的电量").type("BigDecimal"),
                                subsectionWithPath("data.content.energySum").description("电量的价格").type("BigDecimal"),
                                subsectionWithPath("data.content.orderSum").description("订单支付的金额").type("BigDecimal"),
                                subsectionWithPath("data.content.createtime").description("订单创建时间").type("Timestamp"),
                                subsectionWithPath("data.content.paytime").description("订单支付时间").type("Timestamp"),
                                subsectionWithPath("data.content.openBill").description("是否开发票,0不开,1开").type("int"),
                                subsectionWithPath("data.content.billNum").description("发票编号").type("String")
                        )*/));
    }

    /**
     * 测试保存订单的接口
     * @throws Exception
     */
    @Test
    public void saveOrderTest() throws Exception{
        /**请求参数*/
        Map<String, Object> map = new HashMap<>();
        map.put("chargeId",2018);
        map.put("machineNo", "SHA78910");
        map.put("openid", "o8r1_4mkF6L6XLCduvD3gIpV02KI");
        map.put("payChannel",0);
        map.put("energyNum",1);
        map.put("energySum",1);
        map.put("orderSum",1);
        map.put("openBill",0);

        /**构建post请求*/
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/saveOrder").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSON(map).toString());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(document("restful-order-save",
                        requestFields(
                                fieldWithPath("chargeId").description("充电信息主键"),
                                fieldWithPath("machineNo").description("充电桩编号"),
                                fieldWithPath("openid").description("用户唯一标识"),
                                fieldWithPath("payChannel").description("支付渠道"),
                                fieldWithPath("energyNum").description("第三方支付订单号"),
                                fieldWithPath("energySum").description("总电量"),
                                fieldWithPath("orderSum").description("订单金额"),
                                fieldWithPath("openBill").description("是否开发票")
                        )));
    }
}
