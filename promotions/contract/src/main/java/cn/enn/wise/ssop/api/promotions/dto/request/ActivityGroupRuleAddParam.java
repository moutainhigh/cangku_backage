package cn.enn.wise.ssop.api.promotions.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiaby
 */
@Data
@ApiModel("拼团活动规则保存参数实体")
public class ActivityGroupRuleAddParam {

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
    @NotNull
    private ActivityGroupRuleParam activityGroupRuleParam;

    /**
     * 产品信息集合
     */
    @ApiModelProperty("产品信息集合")
    private List<ActivityGoodsAddParam> goods;

    /**
     * 投放渠道id 数组
     */
    @ApiModelProperty("投放渠道id 数组")
    @NotNull
    private List<Long> platformIds;

    /**
     * 退款类型 1 常规退款 2 不予退款
     */
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款 [refundType]")
    @NotNull
    private Byte refundType;

    /**
     * 产品范围 1 全部产品 2 指定产品
     */
    @ApiModelProperty("产品范围 1 全部产品 2 指定产品")
    @NotNull
    private Byte goodsLimit;
}
