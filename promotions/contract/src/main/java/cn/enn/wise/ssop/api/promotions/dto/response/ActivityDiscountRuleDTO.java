package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.MinusRule;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author 石斋
 * 添加优惠活动规则参数
 */
@Data
public class ActivityDiscountRuleDTO {

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private Long activityBaseId;

    /**
     * drawRule
     */
    @ApiModelProperty("早定优惠规则")
    private ReservationRole reservationRole;

    /**
     * 特价直减规则
     */
    @ApiModelProperty("特价直减规则")
    private SaleRole saleRole;

    /**
     * 满减优惠规则
     */
    @ApiModelProperty("满减优惠规则")
    private MinusRule minusRule;

    /**
     * 产品信息集合
     */
    @ApiModelProperty("产品信息集合")
    private List<ActivityGoodsDTO> goods;

    /**
     * 投放渠道id 数组
     */
    @ApiModelProperty("投放渠道id 数组")
    private List<Long> platformIds;

    /**
     * 活动规则描述
     */
    @ApiModelProperty("活动规则描述")
    private String remark;

    /**
     * 优惠算法 1 早定优惠 2 特价直减 3 满减优惠
     */
    @ApiModelProperty("优惠算法 1 早定优惠 2 特价直减 3 满减优惠 [activityType]")
    private Byte algorithm;

    /**
     * 退款类型 1 常规退款 2 不予退款
     */
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款 [refundType]")
    private Byte refundType;

    @Column(name = "goods_limit",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "产品范围 1 全部产品 2 指定产品")
    @ApiModelProperty("产品范围 1 全部产品 2 指定产品")
    private Byte goodsLimit;

    /**
     * 早定优惠
     */
    public static class ReservationRole{
        public ReservationRole() {
        }

        @ApiModelProperty("早定规则列表")
        public List<ReservationRoleAlgorithm> reservationRoleAlgorithms;

    }

    /**
     * 特价直减
     */
    public static class SaleRole{
        public SaleRole() {
        }

        @ApiModelProperty("优惠方式 1 金额 2 折扣 [saleMode]")
        public Byte saleType;

        @ApiModelProperty("优惠折扣或金额，折扣1-100数字")
        public Integer saleDiscountOrMoney;
    }

//    /**
//     * 满减规则
//     */
//    public static class MinusRule{
//        public MinusRule() {
//        }
//
//        @ApiModelProperty("优惠条件 1 订单金额(元) 2 订单人头(位) [preferentialTerms]")
//        public Byte discountConditions;
//
//        @ApiModelProperty("满减算法列表")
//        public List<MinusRuleAlgorithm> minusRuleAlgorithmList;
//
//    }
//
//    /**
//     * 满减规则算法  范围，优惠方式，优惠金额
//     */
//    public static class MinusRuleAlgorithm{
//        public MinusRuleAlgorithm() {
//        }
//
//        @ApiModelProperty("范围开始")
//        public Integer start;
//
//        @ApiModelProperty("范围结束")
//        public Integer end;
//
//        @ApiModelProperty("优惠体现 1 优惠券 2 减免 [discountMode]")
//        public Byte saleType;
//
//        @ApiModelProperty("优惠券id")
//        public Long couponId;
//
//        @ApiModelProperty("优惠券名称")
//        public String couponName;
//
//        @ApiModelProperty("优惠券发放数量")
//        public Integer issueCount;
//
//        @ApiModelProperty("领取次数/人")
//        public Integer numberForDay;
//
//        @ApiModelProperty("减免金额")
//        public Integer salePrice;
//
//
//    }

    public static class ReservationRoleAlgorithm{
        public ReservationRoleAlgorithm() {
        }

        @ApiModelProperty("优惠体现 1 优惠券 2 减免 [discountMode]")
        public Byte saleType;

        @ApiModelProperty("优惠券id")
        public Long couponId;

        @ApiModelProperty("优惠券名称")
        public String couponName;

        @ApiModelProperty("优惠券发放数量")
        public Integer issueCount;

        @ApiModelProperty("领取次数/人")
        public Integer numberForDay;

        @ApiModelProperty("减免金额")
        public Integer salePrice;



        @ApiModelProperty("优惠条件: 提前天数以上 满足条件")
        public Integer dayCount;

    }

    
}
