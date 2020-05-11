package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/23
 */
@Data
public class HotelOrderAdminVo extends HotelOrderVo {


    /**
     * 房型
     */
    @ApiModelProperty(value = "房型")
    private String goodsName;

    /**
     * 订单金额（元）
     */
    @ApiModelProperty(value = "订单金额（元）")
    private String actualPay;

    /**
     * 商品总价
     */
    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "state 订单状态：1，待支付；2，待使用；3，已使用；5，已取消；6，已退票")
    private String state;

    @ApiModelProperty(value = "订单日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "单价")
    private BigDecimal siglePrice;

}
