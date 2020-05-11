package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/9
 */
@Data
public class GoodsCouponPromotionVo {

    @ApiModelProperty("优惠券活动编号")
    private String code;



    @ApiModelProperty("景区Id")
    private Long companyId;


    @ApiModelProperty("景区名称")
    private String companyName;


    @ApiModelProperty("活动景点")
    private String scenicSpots;


    @ApiModelProperty("活动名称")
    private String name;


    @ApiModelProperty("活动状态( 1未开始 2活动中 3已结束 4 已失效)")
    private Byte status;


    @ApiModelProperty("预计成本")
    private BigDecimal cost;


    @ApiModelProperty("活动类型 1 拼团 2 营销")
    private String promotionType;


    @ApiModelProperty("成本部门")
    private String orgName;

    /**
     * 开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    /**
     * 结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Timestamp endTime;


    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("活动对象类型( 1 通用 2 制定人群)")
    private Byte promotionCrowdType;

    @ApiModelProperty("指定人群 1 新人 2 分销商")
    private String promotionCrowd;


    @ApiModelProperty("活动互斥类型( 1 共享 2 互斥)")
    private Byte promotionRejectType;


    @ApiModelProperty("互斥活动")
    private String rejectPromotion;


    @ApiModelProperty("备注：活动规则")
    private String remark;

    @ApiModelProperty("商品列表")
    private List<PromotionAndGoodsVo> goodsList;

    @ApiModelProperty("规则列表")
    private List<GoodsCouponRuleVo> ruleList;

    @ApiModelProperty("优惠券列表")
    private List<GoodsCouponVo> couponList;



}
