package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGoodsAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ActivityGroupRuleParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiaby
 */
@Data
@ApiModel("拼团活动规则返回参数实体")
public class ActivityGroupRuleDetailDTO {

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    @NotNull
    private Long activityBaseId;

    /**
     * 拼团规则信息
     */
    @ApiModelProperty("拼团规则信息")
    private ActivityGroupRuleDTO activityGroupRuleDTO;

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
     * 退款类型 1 常规退款 2 不予退款
     */
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款 [refundType]")
    private Byte refundType;

    /**
     * 产品范围 1 全部产品 2 指定产品
     */
    @ApiModelProperty("产品范围 1 全部产品 2 指定产品")
    private Byte goodsLimit;

    /**
     * 活动类型 1 优惠活动 2 拼团活动 3 抽奖活动
     */
    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;
}
