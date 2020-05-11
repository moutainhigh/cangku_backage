package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/23 14:06
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:App订单返回信息
 ******************************************/
@Data
@ApiModel
public class AppOrderVo implements Serializable {


    private static final long serialVersionUID = 1374637891275667939L;


    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品总价 IOS专用")
    private String goodsPriceIOS;

    @ApiModelProperty(value = "订单状态：1，待支付；2，待使用；3，已使用；4，已过期；5，已取消；6，已退票；7，出票失败;8.退单完成;9 体验完成；10.已核销；11.已退单")
    private Integer state;

    @ApiModelProperty(value = "订单日期")
    private String createTime;

    @ApiModelProperty(value = "购票人手机号")
    private String phone;

    @ApiModelProperty(value = "购票人姓名")
    private String name;

    @ApiModelProperty(value = "使用时段")
    private String timespan;

    @ApiModelProperty(value="分销快照")
    private String snapshot;

    @ApiModelProperty(value="是否分销单")
    private Byte isDistributeOrder;

    @ApiModelProperty(value = "1 线上订单 2 离线订单 3.拼团订单")
    private Integer orderType;

    @ApiModelProperty(value = "1.不可退 2.可退")
    private Integer isCanRefund=1;

    @ApiModelProperty(value = "应收金额")
    private String shouldPay;

    @ApiModelProperty(value = "1.已出票 2.已核验")
    private Integer discriminateBBDSts=1;
}
