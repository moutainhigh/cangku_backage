package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.abstractclass.AbstractThirdOrderContext;
import cn.enn.wise.platform.mall.bean.bo.Orders;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 楠溪江门票订单Context
 *
 * @author baijie
 * @date 2020-02-13
 */
@Data
public class NxjOrderContext extends AbstractThirdOrderContext {

    @ApiModelProperty("游客手机号")
    private String orderTel;

    @ApiModelProperty("游客姓名,多个用英文逗号隔开")
    private String tourismName;

    @ApiModelProperty("供应商Id")
    private Integer supplierId;

    @ApiModelProperty("预下单支付信息")
    private Object prePayInfo;

    @ApiModelProperty("订单信息")
    private Orders orders;

    @ApiModelProperty(value = "门票信息")
    private JSONObject ticketInfo;

    @ApiModelProperty(value = "门票结算价")
    private BigDecimal buyPrice;

    @ApiModelProperty(value = "商品信息")
    private GoodsApiResVO goodsInfo;

    @ApiModelProperty("扩展信息")
    private List<Map<String,Object>> extInfo;

    @ApiModelProperty("身份证号， 逗号隔开")
    private String personId;

    @ApiModelProperty("用户领取优惠券记录的Id")
    private Long userOfCouponId;

    @ApiModelProperty("是否需要支付")
    private Boolean isPay ;

}
