package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分享
 * @author 安辉
 */
@Data
@ApiModel("添加分享参数")
public class ActivityShareAddParam {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("图片分享")
    private String imgShare;

    @ApiModelProperty("链接分享")
    private String linkShare;

    @ApiModelProperty("分享次数")
    @Deprecated
    private Integer shareNumber;

    @ApiModelProperty(value = "分享获得优惠券id")
    @Deprecated
    private Long shareGetCouponId;

    @ApiModelProperty("分享可获得优惠券数量")
    @Deprecated
    private Integer shareGetCouponNumber;

    @ApiModelProperty("分享后被扫描次数")
    private Integer scanNumber;

    @ApiModelProperty(value = "分享后被扫描获得优惠券id")
    private Long scanGetCouponId;

    @ApiModelProperty("分享后被扫描可获得优惠券数量")
    private Integer scanGetCouponNumber;

    @ApiModelProperty("分享后成功订单数")
    private Integer orderNumber;

    @ApiModelProperty(value = "分享后成功下订单获得优惠券id")
    private Long orderGetCouponId;

    @ApiModelProperty("分享后成功下订单可获得优惠券数量")
    private Integer orderGetCouponNumber;

    @ApiModelProperty("活动二维码")
    private String qrCode;

    @ApiModelProperty("规则描述")
    private String description;
}
