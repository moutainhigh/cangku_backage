package cn.enn.wise.ssop.api.order.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * SSOP平台订单基础信息，非具体业务实体类，作为框架指针传递实体业务对象
 */
@Data
public abstract class BaseOrderParam {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "下单人的会员id")
    private Long memberId;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id",required = true)
    private Long activityBaseId;

    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id",required = true)
    private Long channelId;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称",required = true)
    private String channelName;

    /**
     * 第三方订单号
     */
    @ApiModelProperty(value = "第三方订单号")
    private String threeOrderNo;

    /**
     * 订单来源 1小程序 2 APP 3 第三方订单 4  H5
     */
    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5",required = true)
    private Byte orderSource;

    /**
     * 联系人商品列表
     */
    @ApiModelProperty(value = "用户购买商品列表",required = true)
    private List<GoodsInfoParam> goodsInfoParamList;

    /**
     * 下单人姓名
     */
    @ApiModelProperty("下单人姓名")
    private String customerName;

    /**
     * 下单人手机号
     */
    @ApiModelProperty("下单人手机号")
    private String phone;

    /**
     * 下单人证件号
     */
    @ApiModelProperty("下单人证件号")
    private String certificateNo;

    /**
     * 下单人手机号
     */
    @ApiModelProperty("下单人证件类型")
    private Byte certificateType;

    /**
     * 分销商手机号
     */
    @ApiModelProperty("分销商手机号")
    private String distributorPhone;
}
