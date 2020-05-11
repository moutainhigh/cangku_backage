package cn.enn.wise.ssop.api.promotions.dto.response;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author 安辉
 * 添加抽奖规则参数
 */
@Data
@ApiModel("抽奖规则返回DTO")
public class ActivityDrawRuleDTO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 活动规则Id
     */
    @ApiModelProperty(value = "活动规则IdDTO")
    private Long activityRuleId;

    /**
     * 抽奖订单条件,1单笔订单 2多笔订单
     */
    @ApiModelProperty(value = "抽奖订单条件,1单笔订单 2多笔订单")
    private Byte drawOrderCondition;

    /**
     * 抽奖条件,1人头 2金额
     */
    @ApiModelProperty(value = "抽奖条件,1人头 2金额")
    private Byte drawManCondition;

    /**
     * 满足多少价钱的金额抽奖1次
     */
    @ApiModelProperty("满足多少价钱的金额抽奖1次")
    private Integer satisfyPrice;

    /**
     * 次数是否递增,1是 2否
     */
    @ApiModelProperty(value = "次数是否递增,1是 2否")
    private Byte isincrease;

    /**
     * 抽奖次数(次/天/人)
     */
    @ApiModelProperty("抽奖次数(次/天/人)")
    private Integer drawSize;

    /**
     * 抽奖方式 1 九宫格 2 转盘 3 摇一摇
     */
    @ApiModelProperty("抽奖方式 1 九宫格 2 转盘 3 摇一摇")
    private Byte drawType;

    /**
     * 开奖频率 1 每日一次 2 活动终结一次
     */
    @ApiModelProperty("开奖频率 1 每日一次 2 活动终结一次")
    private Byte openDrawCycle;

    /**
     * 兑奖时间类型 1 活动结束后 2中奖后
     */
    @ApiModelProperty("兑奖时间类型 1 活动结束后 2中奖后")
    private Byte cashTimeType;


    /**
     * 兑奖时间
     */
    @ApiModelProperty("兑奖时间")
    private Integer cashTime;

    /**
     * 兑奖标识 1 手机号 2微信号 3 身份证号 4地址
     */
    @ApiModelProperty("兑奖标识 1 手机号 2微信号 3 身份证号 4地址")
    private JSONArray cashCode;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 奖品类型(json)
     */
    @ApiModelProperty(value = "奖品类型(json)")
    private String cash;

    /**
     * 退款类型 1 常规退款 2 不予退款
     */
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;

    /**
     * 产品范围 1 全部产品 2 指定产品
     */
    @ApiModelProperty("产品范围 1 全部产品 2 指定产品")
    private Byte goodsLimit;

    /**
     * 规则描述
     */
    @ApiModelProperty("规则描述")
    private String description;
    
}
