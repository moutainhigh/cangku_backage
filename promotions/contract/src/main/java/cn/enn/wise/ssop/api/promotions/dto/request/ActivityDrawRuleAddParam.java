package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author 安辉
 * 添加抽奖规则参数
 */
@ApiModel("抽奖规则添加参数")
@Data
public class ActivityDrawRuleAddParam {

    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    @NotNull
    private Long activityBaseId;

    /**
     * 抽奖规则基础信息
     */
    @ApiModelProperty("抽奖规则基础信息")
    private ActivityDrawRuleParam drawRule;

    /**
     * 产品信息集合
     */
    @ApiModelProperty("产品信息集合")
    private List<ActivityGoodsAddParam> goods;

    /**
     * 投放渠道id 数组
     */
    @ApiModelProperty("投放渠道id 数组")
    private List<Long> platformIds;

    /**
     * 精品类型结合
     */
    @ApiModelProperty("精品类型结合")
    private List<ActivityDrawRuleCashAddParam> cashList;
}
