package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author 安辉
 * 添加抽奖规则参数
 */
@ApiModel("抽奖规则返回参数")
@Data
public class ActivityDrawRuleDetailDTO {

    /**
     * 活动id
     */
    @ApiModelProperty("返回活动id")
    private Long activityBaseId;

    /**
     * 抽奖规则基础信息
     */
    @ApiModelProperty("返回抽奖规则基础信息")
    private ActivityDrawRuleDTO drawRule;

    /**
     * 产品信息集合
     */
    @ApiModelProperty("返回产品信息集合")
    private List<ActivityGoodsDTO> goods;

    /**
     * 投放渠道id 数组
     */
    @ApiModelProperty("返回投放渠道id 数组")
    private List<Long> platformIds;

    /**
     * 精品类型结合
     */
    @ApiModelProperty("返回精品类型结合")
    private List<ActivityDrawRuleCashDTO> cashList;
}
