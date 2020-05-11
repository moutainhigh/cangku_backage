package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.bean.vo.GoodsCouponVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/13
 */
@Data
@ApiModel("添加活动请求参数")
public class AddGoodsCouponPromotionParam {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("活动景区")
    private String scenicSpots;

    @ApiModelProperty("活动名称")
    private String name ;

    @ApiModelProperty("活动状态")
    private String[] status;

    @ApiModelProperty("成本")
    private BigDecimal cost;

    @ApiModelProperty("活动类型")
    private Byte[] promotionType;

    @ApiModelProperty("分摊部门")
    private String[] orgName;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("活动对象")
    private Integer promotionCrowd;

    @ApiModelProperty("活动对象List")
    private Integer[] promotionCrowdList;

    @ApiModelProperty("是否活动互斥 1 通用 2 互斥")
    private Byte promotionReject;

    @ApiModelProperty("负责人")
    private String remark;

    @ApiModelProperty("商品id")
    private List<Integer> goodsList;

    @ApiModelProperty("优惠券列表")
    private List<GoodsCouponVo> couponList;
}
