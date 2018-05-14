package com.order.test;

import com.alibaba.fastjson.JSONObject;
import com.order.SpringRestDocApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Junit单元测试并且生成文档
 * @author 冯志宇 2018/5/11
 */
public class OrderControllerTest extends SpringRestDocApplicationTests {

    /**
     * 测试根据订单状态分页查询我的订单的api
     * @throws Exception
     */
    @Test
    public void getByStatusTest() throws Exception{
        /**构建请求时
         * 如果是restful风格的url地址，则需要RestDocumentationRequestBuilders类构建
         * 传统url地址则可以使用MockMvcRequestBuilders构建
         * */
        MockHttpServletRequestBuilder requestBuilder =
                RestDocumentationRequestBuilders.get("/getByStatus/{pageNum}/{pageSize}/{openid}/{status}",1,10,"o7VNc5Ri-CX8_FEP37tgw-iE9YXw",2)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("restful-order-profile",
                        /**关于请求参数生成文档
                         * restful传参参数用pathParameters
                         * 问号传参用requestParameters
                         * post传参用requestFields
                         */

                        pathParameters(
                                parameterWithName("pageNum").description("当前页数"),
                                parameterWithName("pageSize").description("每页条数"),
                                parameterWithName("openid").description("用户标识"),
                                parameterWithName("status").description("订单状态,0未付款,1已取消,2已付款")
                        ),
                        responseFields(
                                subsectionWithPath("content[0].id").description("主键标识").type("long"),
                                subsectionWithPath("content[0].chargeId").description("充电信息主键").type("long"),
                                subsectionWithPath("content[0].machineNo").description("充电桩编号").type("String"),
                                subsectionWithPath("content[0].openid").description("用户唯一标识").type("String"),
                                subsectionWithPath("content[0].orderNo").description("订单编号").type("String"),
                                subsectionWithPath("content[0].orderStatus").description("订单状态,0未支付,1已取消,2已支付").type("int"),
                                subsectionWithPath("content[0].payChannel").description("支付渠道").type("0微信,1支付宝"),
                                subsectionWithPath("content[0].outTradeNo").description("第三方支付订单号").type("String"),
                                subsectionWithPath("content[0].energyNum").description("购买的电量").type("BigDecimal"),
                                subsectionWithPath("content[0].energySum").description("电量的价格").type("BigDecimal"),
                                subsectionWithPath("content[0].orderSum").description("订单支付的金额").type("BigDecimal"),
                                subsectionWithPath("content[0].createtime").description("订单创建时间").type("Timestamp"),
                                subsectionWithPath("content[0].paytime").description("订单支付时间").type("Timestamp"),
                                subsectionWithPath("content[0].openBill").description("是否开发票,0不开,1开").type("int"),
                                subsectionWithPath("content[0].billNum").description("发票编号").type("String"),
                                subsectionWithPath("last").description("是否最后一页").type("boolean"),
                                subsectionWithPath("totalPages").description("总页数").type("int"),
                                subsectionWithPath("totalElements").description("总元素数").type("int"),
                                subsectionWithPath("number").description("页数").type("int"),
                                subsectionWithPath("size").description("页面大小").type("int"),
                                subsectionWithPath("sort").description("排序方式").type("Object"),
                                subsectionWithPath("first").description("是否第一页").type("boolean"),
                                subsectionWithPath("numberOfElements").description("当前页元素数").type("int")
                        )));
    }

    /**
     * 测试保存订单的接口
     * @throws Exception
     */
    //@Test
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
                        ),
                        responseFields(
                                fieldWithPath("").description("")
                        )));
    }
}
