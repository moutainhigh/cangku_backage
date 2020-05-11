package cn.enn.wise.ssop.api.promotions.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 安辉
 * 奖品类型参数
 */
@Data
@ApiModel("奖品类型参数")
public class ActivityDrawRuleCashAddParam {

    /**
     * 券id
     */
    @ApiModelProperty("券id")
    private String couponId;

    /**
     * 券名称
     */
    @ApiModelProperty("券id")
    private String couponName;

    /**
     * 券数量
     */
    @ApiModelProperty("券数量")
    private Integer quantity;

    /**
     * 奖品数量（库存）
     */
    @ApiModelProperty("奖品数量（库存）")
    private Integer stock;

    /**
     * 中奖概率 1-100
     */
    @ApiModelProperty("中奖概率")
    private Integer probability;
}
