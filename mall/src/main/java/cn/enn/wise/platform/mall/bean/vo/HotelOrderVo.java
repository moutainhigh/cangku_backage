package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存订单Vo
 *
 * @author baijie
 * @date 2019-09-20
 */
@Data
@ApiModel("保存订单Vo")
public class HotelOrderVo {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 用户微信昵称
     */
    @ApiModelProperty(value = "用户微信昵称")
    private String userWechatName;

    /**
     * 购票人姓名
     */
    @ApiModelProperty(value = "购票人姓名")
    private String name;

    /**
     * 景点Id
     */
    @ApiModelProperty(value = "景点Id")
    private Long scenicId;

    /**
     * 买票数量
     */
    @ApiModelProperty(value = "买票数量")
    private Integer amount;

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private String  goodsId;

    @ApiModelProperty(value = "入住日期")
    private String incomeDate;

    @ApiModelProperty(value = "离店日期")
    private String departureDate;

    @ApiModelProperty(value = "入住天数")
    private Integer dayStayed;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("订单号")
    private String orderCode;
}
